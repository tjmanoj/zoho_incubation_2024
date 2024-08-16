#include <iostream>
#include <string>

// Function to evaluate the expression
int evaluateExpression(const std::string& expr) {
    int length = expr.length();
    int currentNumber = 0;
    int intermediateResult = 0;
    int finalResult = 0;
    char lastOperator = '+';
    char currentOperator = '+';
    
    for (int i = 0; i < length; ++i) {
        char ch = expr[i];
        
        if (ch >= '0' && ch <= '9') {
            currentNumber = currentNumber * 10 + (ch - '0'); 
        } else {
            if (lastOperator == '*') {
                intermediateResult *= currentNumber;
            } else if (lastOperator == '+') {
                intermediateResult += currentNumber;
            } else if (lastOperator == '-') {
                intermediateResult -= currentNumber;
            }
            lastOperator = ch;
            currentNumber = 0;
        }
    }

    if (lastOperator == '*') {
        intermediateResult *= currentNumber;
    } else if (lastOperator == '+') {
        intermediateResult += currentNumber;
    } else if (lastOperator == '-') {
        intermediateResult -= currentNumber;
    }
    
    if (finalResult == 0) {
        finalResult = intermediateResult;
    } else if (currentOperator == '*') {
        finalResult *= intermediateResult;
    } else if (currentOperator == '+') {
        finalResult += intermediateResult;
    } else if (currentOperator == '-') {
        finalResult -= intermediateResult;
    }

    return finalResult;
}

int main() {
    std::string input = "123+*";

    int result = evaluateExpression(input);
    std::cout << "Result: " << result << std::endl;

    return 0;
}

