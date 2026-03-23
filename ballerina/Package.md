# Ballerina module for WSO2 Integrator:MI module generation

## Overview

The `wso2/mi` module provides the capability to generate modules that allow WSO2 Integrator:MI to run Ballerina transformations. This integration enables you to leverage Ballerina's powerful transformation capabilities within the WSO2 Micro Integrator environment.

Unlike Class Mediators, Ballerina is a cloud-native programming language with built-in support for JSON and XML, making data transformations simpler. It also allows the use of available Ballerina language modules and connectors, enabling enhanced functionality and easier connectivity with external systems.

## Compatibility

This module is compatible with Ballerina `2201.10.x` and above, Java 17, and WSO2 MI `4.4.0+`.

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

### Pull the `migen` tool

First, pull the `migen` tool which is used to create the WSO2 MI module:

```bash
bal tool pull migen
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

Use the `bal migen module` command to generate the WSO2 Micro Integrator module from your Ballerina project:

```bash
bal migen module -i <path_to_ballerina_project>
```

The command generates the module zip in the same location.

## Using with WSO2 MI VS Code Extension

1. Install the [WSO2 Integrator:MI VS Code Extension](https://mi.docs.wso2.com/en/latest/develop/mi-for-vscode/install-wso2-mi-for-vscode/)
2. Create a new integration project or open an existing project
3. Add a **Ballerina Module** artifact
4. Enter a name and version for the module
5. Update the generated sample Ballerina code with your transformation logic
6. Click the **Build Ballerina Module** icon to build the module

Once built, the Ballerina module will appear in the Mediator Palette and can be used in integration flows.
