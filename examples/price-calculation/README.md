## Overview

This sample demonstrates the use of a connector generated by the Ballerina MI SDK to perform price related calculation of an order. 
In this example, the resource `price-calculation` accepts an XML payload containing item details and calculates the total invoice value.
This calculation is done by a function implemented in Ballerina, which processes an input payload of the following format:

```xml
<items>
    <item>
        <name>{item-name}</name>
        <price>{price}</price>
    </item>
    ...
</items>
```

This would output a result in the following format.

```xml
<total xmlns="http://ws.apache.org/ns/synapse">{total}</total>
```

## Steps to Invoke the Sample

Follow these steps to invoke the sample using the connector:

1. The `bal-price-calculation` folder contains the Ballerina code for the connector. Invoke the following command to generate the connector:

    ```bash
    bal mi -i <ballerina-project>
    ```

2. A ZIP file of the connector will be generated. Add this ZIP file to the MI project inside the folder `mi-price-calculation` following the approach described [here](https://mi.docs.wso2.com/en/latest/develop/creating-artifacts/adding-connectors/).

3. Once the connector is added, run the MI project.

4. Send an HTTP POST request to the following resource with a payload as specified:

```bash
curl --location 'http://localhost:8290/price-calculation' \
--header 'Content-Type: application/xml' \
--data '<items>
      <item>
         <name>book</name>
         <price>180</price>
      </item>
      <item>
         <name>pen</name>
         <price>25</price>
      </item>
      <item>
         <name>pencil</name>
         <price>12</price>
      </item>
   </items>`'
```

Output:

```xml
<total xmlns="http://ws.apache.org/ns/synapse">217</total>
```