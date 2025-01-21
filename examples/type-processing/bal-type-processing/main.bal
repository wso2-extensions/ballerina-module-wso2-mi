import wso2/mi;

@mi:Operation
public function invertBoolean(boolean b) returns boolean => !b;

@mi:Operation
public function doubleInt(int n) returns int => n * 2;

@mi:Operation
public function reciprocalFloat(float f) returns float {
    if f == 0.0 {
        return 1;
    }
    return 1.0 / f;
}

@mi:Operation
public function addConstantToDecimal(decimal d) returns decimal => d + 10;

@mi:Operation
public function doubleString(string s) returns string => s + s;

@mi:Operation
public function getJsonNameProperty(json j) returns json {
    json jsn = j;
    if jsn is string {
        json|error je = jsn.fromJsonString();
        if je is error {
            return {err: je.message()};
        }
        jsn = je;
    }
    json|error val = jsn.name;
    if val is error {
        return {err: val.message()};
    }
    return {val};
}

@mi:Operation
public function getXmlNameElement(xml x) returns xml {
    xml y = x/<name>;
    return xml `<result>${y}</result>`;
}
