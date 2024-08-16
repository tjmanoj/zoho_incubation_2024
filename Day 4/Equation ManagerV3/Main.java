import java.util.Arrays;

class Equation {
    Term[] terms;
    VariableValue[] variableValues;

    Equation(int termSize, VariableValue[] variableValues) {
        terms = new Term[termSize];
        this.variableValues = variableValues;
    }

    public void display() {
        System.out.println("Equation [terms=" + Arrays.toString(terms) +
                           ", variableValues=" + Arrays.toString(variableValues) + "]");
    }

    public Equation multiply(String equationString) {
        Equation secondEquation = EquationManager.parseEquation(equationString);
        Equation resultEquation = new Equation(this.terms.length * secondEquation.terms.length,
                                               this.variableValues);

        int count = 0;
        for (Term firstTerm : this.terms) {
            for (Term secondTerm : secondEquation.terms) {
                resultEquation.terms[count++] = firstTerm.multiply(secondTerm);
            }
        }
        return resultEquation;
    }

    public double evaluate(double[] values) {
        // Assign values to variableValue objects
        for (int i = 0; i < variableValues.length; i++) {
            variableValues[i].value = values[i];
        }

        double result = 0.0;
        for (Term term : terms) {
            // Pass only the relevant VariableValue objects for each term
            VariableValue[] relevantVariables = getRelevantVariableValues(term);
            result += term.evaluate(relevantVariables);
        }
        return result;
    }

    private VariableValue[] getRelevantVariableValues(Term term) {
        // Collect relevant VariableValue objects for this term
        VariableValue[] relevantVariables = new VariableValue[term.getVariableCount()];
        int index = 0;
        for (Variable var : term.variables) {
            for (VariableValue varValue : variableValues) {
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
               ", variableValues=" + Arrays.toString(variableValues) + "]";
    }
}

class Term {
    int coefficient;
    Variable[] variables;

    Term(int size) {
        variables = new Variable[size];
    }

    public Term multiply(Term secondTerm) {
        int[] powerMap = new int[26];
        int resultCoefficient = this.coefficient * secondTerm.coefficient;

        loadVariablesToMap(powerMap, this.variables);
        loadVariablesToMap(powerMap, secondTerm.variables);

        Term resultTerm = new Term(getNonZeroCount(powerMap));
        resultTerm.variables = mapToVariableArray(powerMap);
        resultTerm.coefficient = resultCoefficient;
        return resultTerm;
    }

    private void loadVariablesToMap(int[] powerMap, Variable[] variables) {
        if (variables != null) {
            for (Variable variable : variables) {
                powerMap[variable.name - 'a'] += variable.power;
            }
        }
    }

    private int getNonZeroCount(int[] map) {
        int count = 0;
        for (int value : map) {
            if (value > 0) count++;
        }
        return count;
    }

    private Variable[] mapToVariableArray(int[] map) {
        Variable[] variables = new Variable[getNonZeroCount(map)];
        int index = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i] > 0) {
                variables[index++] = new Variable((char) (i + 'a'), map[i]);
            }
        }
        return variables;
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
            term.variables[0] = variable;
        }

        return term;
    }

    public double evaluate(VariableValue[] variableValues) {
        double result = coefficient;
        if (variables != null) {
            for (Variable variable : variables) {
                for (VariableValue varValue : variableValues) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(coefficient);
        if (variables != null) {
            for (Variable variable : variables) {
                sb.append(variable.name).append("^").append(variable.power);
            }
        }
        return sb.toString();
    }

    public int getVariableCount() {
        return variables != null ? variables.length : 0;
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
        String[] termsInPrimitive = splitTerms(equationInString); // Split into terms

        // Extract distinct variables and create VariableValue objects
        Variable[] variables = extractVariables(termsInPrimitive);
        VariableValue[] variableValues = new VariableValue[variables.length];
        for (int i = 0; i < variables.length; i++) {
            variableValues[i] = new VariableValue(variables[i].name, 0); // Initialize with 0
        }

        // Create Equation
        Equation equation = new Equation(termsInPrimitive.length, variableValues);
        for (int i = 0; i < termsInPrimitive.length; i++) {
            equation.terms[i] = Term.parseTerm(termsInPrimitive[i]);
        }
        return equation;
    }

    private static Variable[] extractVariables(String[] terms) {
        java.util.Set<Character> variableSet = new java.util.HashSet<>();
        for (String term : terms) {
            for (char ch : term.toCharArray()) {
                if (Character.isLetter(ch)) {
                    variableSet.add(ch);
                }
            }
        }
        Variable[] variables = new Variable[variableSet.size()];
        int index = 0;
        for (char ch : variableSet) {
            variables[index++] = new Variable(ch, 0); // Initialize with power 0
        }
        return variables;
    }

    public static String[] splitTerms(String input) {
        String[] tempArray = new String[input.length()];
        int index = 0;
        String regex = "([+-]?[^-+]+)";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input.replaceAll(" ", ""));

        while (matcher.find()) {
            tempArray[index++] = matcher.group();
        }
        String[] termArray = new String[index];
        System.arraycopy(tempArray, 0, termArray, 0, index);
        return termArray;
    }
}

public class Main {
    public static void main(String[] args) {
        Equation equation = EquationManager.parseEquation("2x+y");
        
        double[] values = {2, 12};
        double evaluationResult = equation.evaluate(values);

        System.out.println(equation);
        System.out.println(evaluationResult);
    }
}
