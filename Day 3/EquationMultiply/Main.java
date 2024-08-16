import java.util.Arrays;

class Equation {
    Term[] equationTerms;
    String[] variableNames;

    Equation(int size) {
        equationTerms = new Term[size];
    }

    public void display() {
        for (Term term : equationTerms) {
            System.out.print(term + " ");
        }
        System.out.println();
    }

    public Equation multiply(String equationString) {
        Equation secondEquation = EquationManager.parseEquation(equationString);
        Equation resultEquation = new Equation(this.equationTerms.length * secondEquation.equationTerms.length);

        int count = 0;
        for (Term firstTerm : this.equationTerms) {
            for (Term secondTerm : secondEquation.equationTerms) {
                resultEquation.equationTerms[count++] = firstTerm.multiply(secondTerm);
            }
        }
        return resultEquation;
    }

    public double evaluate(double[] values) {
        double result = 0.0;
        for (Term term : equationTerms) {
            result += term.evaluate(variableNames, values);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Equation [terms=" + Arrays.toString(equationTerms) + ", variables=" + Arrays.toString(variableNames) + "]";
    }
}

class Term {
    int coefficient;
    Variable[] termVariables;

    Term(int size) {
        termVariables = new Variable[size];
    }

    public Term multiply(Term secondTerm) {
        int[] powerMap = new int[26];
        int resultCoefficient = this.coefficient * secondTerm.coefficient;

        loadVariablesToMap(powerMap, this.termVariables);
        loadVariablesToMap(powerMap, secondTerm.termVariables);

        Term resultTerm = new Term(getNonZeroCount(powerMap));
        resultTerm.termVariables = mapToVariableArray(powerMap);
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
            term.termVariables[0] = variable;
        }

        return term;
    }

    public double evaluate(String[] variableNames, double[] values) {
        double result = coefficient;
        if (termVariables != null) {
            for (Variable variable : termVariables) {
                for (int i = 0; i < variableNames.length; i++) {
                    if (variable.name == variableNames[i].charAt(0)) {
                        result *= Math.pow(values[i], variable.power);
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
        if (termVariables != null) {
            for (Variable variable : termVariables) {
                sb.append(variable.name).append("^").append(variable.power);
            }
        }
        return sb.toString();
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

class EquationManager {
    public static Equation parseEquation(String equationInString) {
        equationInString = equationInString.trim();
        String[] termsInPrimitive = splitTerms(equationInString); // Split into terms

        int count = 0;
        Equation equation = new Equation(termsInPrimitive.length);
        for (String termInPrimitive : termsInPrimitive) {
            equation.equationTerms[count++] = Term.parseTerm(termInPrimitive);
        }
        
        equation.variableNames = extractVariableNames(equationInString);
        return equation;
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

    public static String[] extractVariableNames(String equationInString) {
        java.util.Set<String> variableSet = new java.util.HashSet<>();
        for (char ch : equationInString.toCharArray()) {
            if (Character.isLetter(ch)) {
                variableSet.add(String.valueOf(ch));
            }
        }
        return variableSet.toArray(new String[0]);
    }
}

public class Main {
    public static void main(String[] args) {
        Equation equation = EquationManager.parseEquation("2x+y");
        
        // Equation result = equation.multiply("2x-3y");
        
        double[] values = {2, 1};
        double evaluationResult = equation.evaluate(values);

        System.out.println(equation);
        System.out.println(evaluationResult);
    }
}
