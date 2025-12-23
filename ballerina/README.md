# Ballerina WSO2 Micro Integrator Module

## Overview

The `wso2/mi` module provides the capability to generate modules that allow WSO2 Micro Integrator (MI) to run Ballerina transformations. This integration enables you to leverage the powerful transformation capabilities of Ballerina within the WSO2 Micro Integrator environment.

Unlike Class Mediators, Ballerina is a cloud-native programming language with built-in support for JSON and XML, making data transformations simpler. It also allows the use of available Ballerina language modules and connectors, enabling enhanced functionality and easier connectivity with external systems.

## Compatibility

| `wso2/mi` version | Ballerina version | Java version | WSO2 MI version |
|:-----------------:|:-----------------:|:------------:|:---------------:|
| 0.2               |     2201.9.2      | 17           |      4.4.0      |
| 0.3               |     2201.11.0     | 21           |      4.4.0      |
| 0.4               |     2201.12.0     | 21           |  4.4.0, 4.5.0   |

## Supported data types

The following Ballerina data types are supported for function parameters and return types:

| Data Type | Parameter | Return Type |
|:---------:|:---------:|:-----------:|
| `boolean` | ✓         | ✓           |
| `int`     | ✓         | ✓           |
| `float`   | ✓         | ✓           |
| `decimal` | ✓         | ✓           |
| `string`  | ✓         | ✓           |
| `xml`     | ✓         | ✓           |
| `json`    | ✓         | ✓           |
| `record`  | ✓         | ✓           |
| `map`     | ✓         | ✓           |
| `array`   | ✓         | ✓           |
| `any`     | ✗         | ✓           |
| `()`      | ✗         | ✓           |

## Usage

### Pull the `mi-module-gen` tool

First, pull the `mi-module-gen` tool which is used to create the WSO2 MI module:

```bash
bal tool pull mi-module-gen
```

### Import the module

Import the `wso2/mi` module in your Ballerina program:

```ballerina
import wso2/mi;
```

### Write a Ballerina transformation

Write your Ballerina transformation using the `@mi:Operation` annotation:

```ballerina
import wso2/mi;

@mi:Operation
public function gpa(xml rawMarks, xml credits) returns xml {
    // Your logic to calculate the GPA
}
```

The Ballerina function annotated with `@mi:Operation` maps to an operation in the generated Ballerina module.

### Generate the module

Use the `bal mi-module-gen` command to generate the WSO2 Micro Integrator module from your Ballerina project:

```bash
bal mi-module-gen -i <path_to_ballerina_project>
```

The command generates the module zip in the same location.

## Using with WSO2 MI VS Code Extension

1. Install the [WSO2 Micro Integrator VS Code Extension](https://mi.docs.wso2.com/en/latest/develop/mi-for-vscode/install-wso2-mi-for-vscode/)
2. Create a new integration project or open an existing project
3. Add a **Ballerina Module** artifact
4. Enter a name and version for the module
5. Update the generated sample Ballerina code with your transformation logic
6. Click the **Build Ballerina Module** icon to build the module

Once built, the Ballerina module will appear in the Mediator Palette and can be used in integration flows.
