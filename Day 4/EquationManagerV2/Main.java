import java.util.Arrays;

class Equation {
    Term[] terms;
    VariableValue[] variableValuesList;

    Equation(int termSize, VariableValue[] variableValuesList) {
        terms = new Term[termSize];
        this.variableValuesList = variableValuesList;
    }

    public void display() {
        System.out.println("Equation [terms=" + Arrays.toString(terms) + ", variableValuesList=" + Arrays.toString(variableValuesList) + "]");
    }

    public Equation multiply(String equationString) {
        Equation secondEquation = EquationManager.parseEquation(equationString);
        Equation resultEquation = new Equation(this.terms.length * secondEquation.terms.length,this.variableValuesList);

        int index = 0;
        for (Term firstTerm : this.terms) {
            for (Term secondTerm : secondEquation.terms) {
                resultEquation.terms[index++] = firstTerm.multiply(secondTerm);
            }
        }
        return resultEquation;
    }

    public double evaluate(double[] values) {
        // Assign values to variableValue objects
        for (int i = 0; i < variableValuesList.length; i++) {
            variableValuesList[i].value = values[i];
        }

        double result = 0.0;
        for (Term term : terms) {
            VariableValue[] relevantVariables = getRelevantVariableValues(term);
            result += term.evaluate(relevantVariables);
        }
        return result;
    }

    private VariableValue[] getRelevantVariableValues(Term term) {
        // Collect relevant VariableValue objects for this term
        VariableValue[] relevantVariables = new VariableValue[term.getVariableCount()];
        int index = 0;
        for (Variable var : term.variablesArray) {
            for (VariableValue varValue : variableValuesList) {
                if (varValue.name == var.name) {
                    relevantVariables[index++] = varValue;
                    break;
                }
            }
        }
        return relevantVariables;
    }

    @Override
    public String toString() {
        return "Equation [terms=" + Arrays.toString(terms) +
               ", variableValuesList=" + Arrays.toString(variableValuesList) + "]";
    }
}

class Term {
    int coefficient;
    Variable[] variablesArray;

    Term(int size) {
        variablesArray = new Variable[size];
    }

    public Term multiply(Term secondTerm) {
        int[] powerMap = new int[26];
        int resultCoefficient = this.coefficient * secondTerm.coefficient;

        loadVariablesToMap(powerMap, this.variablesArray);
        loadVariablesToMap(powerMap, secondTerm.variablesArray);

        Term resultTerm = new Term(getNonZeroCount(powerMap));
        resultTerm.variablesArray = mapToVariableArray(powerMap);
        resultTerm.coefficient = resultCoefficient;
        return resultTerm;
    }

    private void loadVariablesToMap(int[] powerMap, Variable[] variablesArray) {
        if (variablesArray != null) {
            for (Variable variable : variablesArray) {
                powerMap[variable.name - 'a'] += variable.power;
            }
        }
    }

    private int getNonZeroCount(int[] map) {
        int VariablesCount = 0;
        for (int value : map) {
            if (value > 0) VariablesCount++;
        }
        return VariablesCount;
    }

    private Variable[] mapToVariableArray(int[] PowerMap) {
        Variable[] variablesArray = new Variable[getNonZeroCount(PowerMap)];
        int index = 0;
        for (int i = 0; i < PowerMap.length; i++) {
            if (PowerMap[i] > 0) {
                variablesArray[index++] = new Variable((char) (i + 'a'), PowerMap[i]);
            }
        }
        return variablesArray;
    }

    public static Term parseTerm(String termInString) {
        termInString = termInString.trim();
        StringBuilder coefficientBuilder = new StringBuilder();
        StringBuilder variableBuilder = new StringBuilder();
        int index = 0;

        if (termInString.charAt(index) == '-') {
            coefficientBuilder.append(termInString.charAt(index));
            index++;
        } else if (termInString.charAt(index) == '+') {
            index++;
        }

        boolean foundVariable = false;
        for (; index < termInString.length(); index++) {
            char ch = termInString.charAt(index);
            if (Character.isDigit(ch)) {
                if (foundVariable) {
                    variableBuilder.append(ch);
                } else {
                    coefficientBuilder.append(ch);
                }
            } else if (ch == '^') {
                foundVariable = true;
            } else {
                variableBuilder.append(ch);
                foundVariable = true;
            }
        }

        int coefficient = coefficientBuilder.length() > 0 ? Integer.parseInt(coefficientBuilder.toString()) : 1;
        Variable variable = variableBuilder.length() > 0 ? Variable.parseVariable(variableBuilder.toString()) : null;
        Term term = new Term(variable != null ? 1 : 0);
        term.coefficient = coefficient;
        if (variable != null) {
            term.variablesArray[0] = variable;
        }

        return term;
    }

    public double evaluate(VariableValue[] variableValuesList) {
        double result = coefficient;
        if (variablesArray != null) {
            for (Variable variable : variablesArray) {
                for (VariableValue varValue : variableValuesList) {
                    if (varValue.name == variable.name) {
                        result *= Math.pow(varValue.value, variable.power);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(coefficient);
        if (variablesArray != null) {
            for (Variable variable : variablesArray) {
                res.append(variable.name).append("^").append(variable.power);
            }
        }
        return res.toString();
    }

    public int getVariableCount() {
        return variablesArray != null ? variablesArray.length : 0;
    }
}

class Variable {
    char name;
    int power;

    Variable(char name, int power) {
        this.name = name;
        this.power = power;
    }

    public static Variable parseVariable(String varString) {
        char name = varString.charAt(0);
        int power = varString.length() > 1 ? Integer.parseInt(varString.substring(1)) : 1;
        return new Variable(name, power);
    }

    @Override
    public String toString() {
        return name + "^" + power;
    }
}

class VariableValue {
    char name;
    double value;

    VariableValue(char name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}

class EquationManager {
    public static Equation parseEquation(String equationInString) {
        equationInString = equationInString.trim();
        String[] unprocessedTerms = splitTerms(equationInString);

        Variable[] variablesArray = extractVariables(unprocessedTerms);
        VariableValue[] variableValuesList = new VariableValue[variablesArray.length];
        
        for (int i = 0; i < variablesArray.length; i++) {
            variableValuesList[i] = new VariableValue(variablesArray[i].name, 0); 
        }

        Equation equation = new Equation(unprocessedTerms.length, variableValuesList);
        for (int i = 0; i < unprocessedTerms.length; i++) {
            equation.terms[i] = Term.parseTerm(unprocessedTerms[i]);
        }
        return equation;
    }

    private static Variable[] extractVariables(String[] terms) {
        char[] initialVariableArray = new char[26]; 
        int UniqueVariablesCount = 0;

        // generating unique variablesArray
        for (String term : terms) {
            for (char ch : term.toCharArray()) {
                if (Character.isLetter(ch)) {
                    boolean found = false;
                    for (int i = 0; i < UniqueVariablesCount; i++) {
                        if (initialVariableArray[i] == ch) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        initialVariableArray[UniqueVariablesCount++] = ch;
                    }
                }
            }
        }

        Variable[] variablesArray = new Variable[UniqueVariablesCount];
        for (int i = 0; i < UniqueVariablesCount; i++) {
            variablesArray[i] = new Variable(initialVariableArray[i], 0); // Initialize with power 0
        }
        return variablesArray;
    }

    public static String[] splitTerms(String input) {
        String[] resultArray = new String[input.length()];
        int index = 0;
        String regex = "([+-]?[^-+]+)";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input.replaceAll(" ", ""));

        while (matcher.find()) {
            resultArray[index++] = matcher.group();
        }
        String[] termArray = new String[index];
        System.arraycopy(resultArray, 0, termArray, 0, index);
        return termArray;
    }
}


public class Main {
    public static void main(String[] args) {
        Equation equation = EquationManager.parseEquation("2x+y");
        
        double[] values = {2, 2};
        double evaluationResult = equation.evaluate(values);

        System.out.println(equation);
        System.out.println(evaluationResult);
    }
}
