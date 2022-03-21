import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.EmptyStackException;
import java.util.Stack;

public class InfToPost {
	private final String OPERATORS = "+-*/^";
	private final String OPEN_BRACKET = "(";
	private final String CLOSE_BRACKET = ")";

	private String input;
	private final StringBuilder output = new StringBuilder();
	private final Stack stack = new Stack();

	public InfToPost(String input) {
		this.input = input;
	}

	public void createPostfix() {
		System.out.println("    Входная строка");
		System.out.println("        " + getInput());

		deleteSpaces();
		setMulti();

		System.out.println("    Убираем пробелы и расставляем *");
		System.out.println("        " + getInput());

		int index = 0;

		while (index < input.length()) {
			String token = Character.toString(input.charAt(index));

			if (isNumber(token)) {
				for (int i = index + 1; i < input.length(); i++) {
					if (!isNumber(Character.toString(input.charAt(i)))) {
						break;
					}

					index = i;
					token = token.concat(Character.toString(input.charAt(i)));
				}

				output.append(token).append(" ");
			} else if (isCharacter(token)) {
				output.append(token).append(" ");
			} else if (isOpenBracket(token)) {
				stack.push(token);
			} else if (isCloseBracket(token)) {
				try {
					while (!stack.empty() & !isOpenBracket(stack.peek().toString())) {
						output.append(stack.pop()).append(" ");
					}

					stack.pop();
				} catch (EmptyStackException ignored) {
				}
			} else if (isOperator(token)) {
				try {
					while (!stack.empty() && isOperator(stack.peek().toString()) &&
							getPriority(token) <= getPriority(stack.peek().toString())) {
						output.append(stack.pop()).append(" ");
					}
				} catch (EmptyStackException ignored) {
				}

				stack.push(token);
			}

			index++;
		}

		while (!stack.empty()) {
			output.append(stack.pop()).append(" ");
		}
	}

	private boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
		} catch (Exception e) {
			return string.contains("var") || string.equals("I");
		}

		return true;
	}

	private boolean isCharacter(String string) {
		if (string.charAt(0) >=  'a' & string.charAt(0) <= 'z') {
			return true;
		}

		return false;
	}

	private boolean isOperator(String string) {
		return OPERATORS.contains(string);
	}

	private boolean isOpenBracket(String string) {
		return OPEN_BRACKET.equals(string);
	}

	private boolean isCloseBracket(String string) {
		return CLOSE_BRACKET.equals(string);
	}

	private int getPriority(String string) {
		if (string.equals("+") || string.equals("-")) {
			return 0;
		} else if (string.equals("*") || string.equals("/")) {
			return 1;
		} else if (string.equals("^")) {
			return 2;
		}

		return -1;
	}

	private void deleteSpaces() {
		input = input.replaceAll(" ", "");
	}

	private void setMulti() {
		for (int i = 0; i < input.length() - 1; i++) {
			String first = Character.toString(input.charAt(i));
			String second = Character.toString(input.charAt(i + 1));

			StringBuilder temp = new StringBuilder(input);
			String multi = first + "*" + second;

			if (isCloseBracket(first) & isOpenBracket(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isNumber(first) & isOpenBracket(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isCharacter(first) & isOpenBracket(second)) {
				input = temp.replace(i , i + 2, multi).toString();
			} else if (isCloseBracket(first) & isNumber(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isCloseBracket(first) & isCharacter(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isNumber(first) & isCharacter(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isCharacter(first) & isNumber(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			} else if (isCharacter(first) & isCharacter(second)) {
				input = temp.replace(i, i + 2, multi).toString();
			}
		}
	}

	private String getInput() {
		return input;
	}

	public String getPostfix() {
		return output.toString();
	}
}
