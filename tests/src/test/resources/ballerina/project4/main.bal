import wso2/mi;

@mi:Operation {
}
public function test(xml xmlA, xml xmlB, xml xmlC) returns xml|error {
    xml ans =xml `<apr30>8:585555551</apr30>`;
    return xmlA + ans;
}

@mi:Operation {
}
public function test1(map<string> m) returns int {
    return 2;
}
