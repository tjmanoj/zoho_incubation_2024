import java.util.Arrays;

class Equation {
    Term[] terms;
    String[] variables;

    Equation(int size) {
        terms = new Term[size];
    }

    public void display() {
        for (Term term : terms) {
            System.out.print(term + " ");
        }
        System.out.println();
    }

    public Equation multiply(String equationString) {
        Equation secondEquation = EquationManager.parseEquation(equationString);
        Equation resultEquation = new Equation(this.terms.length * secondEquation.terms.length);

        int count = 0;
        for (Term firstTerm : this.terms) {
            for (Term secondTerm : secondEquation.terms) {
                resultEquation.terms[count++] = firstTerm.multiply(secondTerm);
            }
        }
        return resultEquation;
    }

    public double evaluate(double[] values) {
        double result = 0.0;
        for (Term term : terms) {
            result += term.evaluate(variables, values);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Equation [terms=" + Arrays.toString(terms) + ", variables=" + Arrays.toString(variables) + "]";
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

    public double evaluate(String[] variableNames, double[] values) {
        double result = coefficient;
        if (variables != null) {
            for (Variable variable : variables) {
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
        StringBuilder res = new StringBuilder();
        res.append(coefficient);
        if (variables != null) {
            for (Variable variable : variables) {
                res.append(variable.name).append("^").append(variable.power);
            }
        }
        return res.toString();
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
            equation.terms[count++] = Term.parseTerm(termInPrimitive);
        }
        
        equation.variables = extractVariables(equationInString);
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

    public static String[] extractVariables(String equationInString) {
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
