/*
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.mi.plugin;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.api.symbols.AnnotationSymbol;
import io.ballerina.compiler.api.symbols.FunctionSymbol;
import io.ballerina.compiler.api.symbols.ParameterSymbol;
import io.ballerina.compiler.api.symbols.TypeDescKind;
import io.ballerina.compiler.api.symbols.TypeSymbol;
import io.ballerina.compiler.api.symbols.Symbol;
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.FunctionDefinitionNode;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.SyntaxNodeAnalysisContext;
import io.ballerina.tools.diagnostics.DiagnosticFactory;
import io.ballerina.tools.diagnostics.DiagnosticInfo;
import io.ballerina.tools.diagnostics.DiagnosticSeverity;

import java.util.List;
import java.util.Optional;

import static io.ballerina.stdlib.mi.plugin.DiagnosticCode.UNSUPPORTED_PARAM_TYPE;
import static io.ballerina.stdlib.mi.plugin.DiagnosticCode.UNSUPPORTED_RETURN_TYPE;
import static io.ballerina.stdlib.mi.plugin.MICompilerPluginUtils.getParamTypeName;
import static io.ballerina.stdlib.mi.plugin.MICompilerPluginUtils.getReturnTypeName;

public class AnnotationAnalysisTask implements AnalysisTask<SyntaxNodeAnalysisContext> {

    private static final String ANNOTATION_QUALIFIER = "Operation";

    private static void checkParametersAndReturnType(SyntaxNodeAnalysisContext context, FunctionSymbol functionSymbol) {
        Optional<List<ParameterSymbol>> params = functionSymbol.typeDescriptor().params();
        if (params.isPresent()) {
            for (ParameterSymbol parameterSymbol : params.get()) {
                TypeSymbol typeSymbol = parameterSymbol.typeDescriptor();
                // Resolve type references to get the actual type
                TypeDescKind typeKind = getActualTypeKind(typeSymbol);
                String paramType = getParamTypeName(typeKind);
                if (paramType == null) {
                    DiagnosticInfo diagnosticInfo = new DiagnosticInfo(UNSUPPORTED_PARAM_TYPE.diagnosticId(),
                            UNSUPPORTED_PARAM_TYPE.message(), DiagnosticSeverity.ERROR);
                    context.reportDiagnostic(DiagnosticFactory.createDiagnostic(diagnosticInfo,
                            parameterSymbol.getLocation().get()));
                }
            }
        }

        Optional<TypeSymbol> optReturnTypeSymbol = functionSymbol.typeDescriptor().returnTypeDescriptor();
        if (optReturnTypeSymbol.isEmpty()) {
            return;
        }
        TypeSymbol returnTypeSymbol = optReturnTypeSymbol.get();
        // Resolve type references to get the actual type
        TypeDescKind returnTypeKind = getActualTypeKind(returnTypeSymbol);
        String returnType = getReturnTypeName(returnTypeKind);
        if (returnType == null) {
            DiagnosticInfo diagnosticInfo = new DiagnosticInfo(UNSUPPORTED_RETURN_TYPE.diagnosticId(),
                    UNSUPPORTED_RETURN_TYPE.message(), DiagnosticSeverity.ERROR);
            context.reportDiagnostic(DiagnosticFactory.createDiagnostic(diagnosticInfo,
                    functionSymbol.getLocation().get()));
        }
    }
    
    /**
     * Get the actual TypeDescKind by resolving type references.
     * For example, if Employee is a record type, TYPE_REFERENCE will be resolved to RECORD.
     */
    private static TypeDescKind getActualTypeKind(TypeSymbol typeSymbol) {
        TypeDescKind typeKind = typeSymbol.typeKind();
        // If it's a type reference, resolve it to get the actual type recursively
        if (typeKind == TypeDescKind.TYPE_REFERENCE) {
            if (typeSymbol instanceof io.ballerina.compiler.api.symbols.TypeReferenceTypeSymbol typeRef) {
                return getActualTypeKind(typeRef.typeDescriptor());
            }
        }
        return typeKind;
    }

    private static FunctionSymbol getFunctionSymbol(SyntaxNodeAnalysisContext context, SemanticModel semanticModel) {
        if (!(context.node() instanceof AnnotationNode annotationNode)) return null;
        Optional<Symbol> symbol = semanticModel.symbol(annotationNode);
        if (symbol.isEmpty()) return null;
        if (!(symbol.get() instanceof AnnotationSymbol annotationSymbol)) return null;
        Optional<String> annotationName = annotationSymbol.getName();
        if (annotationName.isEmpty()) return null;
        if (!annotationName.get().equals(ANNOTATION_QUALIFIER)) return null;

        if (!(annotationNode.parent().parent() instanceof FunctionDefinitionNode functionNode)) return null;
        Optional<Symbol> funcSymbol = semanticModel.symbol(functionNode);
        if (funcSymbol.isEmpty()) return null;
        if (!(funcSymbol.get() instanceof FunctionSymbol functionSymbol)) return null;
        return functionSymbol;
    }

    @Override
    public void perform(SyntaxNodeAnalysisContext context) {
        SemanticModel semanticModel = context.compilation().getSemanticModel(context.moduleId());
        FunctionSymbol functionSymbol = getFunctionSymbol(context, semanticModel);
        if (functionSymbol == null) return;

        Optional<String> functionName = functionSymbol.getName();
        if (functionName.isEmpty()) return;
        checkParametersAndReturnType(context, functionSymbol);
    }
}
