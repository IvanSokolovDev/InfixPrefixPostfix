import java.util.EmptyStackException;
import java.util.Stack;

public class InfToPref {
	private final String OPERATORS = "+-*/^";
	private final String OPEN_BRACKET = "(";
	private final String CLOSE_BRACKET = ")";

	private String input;
	private StringBuilder output = new StringBuilder();
	private final Stack stack = new Stack();

	public InfToPref(String input) {
		this.input = input;
	}

	public void createPrefix() {
		System.out.println("    Входная строка");
		System.out.println("        " + getInput());

		deleteSpaces();
		setMulti();

		System.out.println("    Убираем пробелы и расставляем *");
		System.out.println("        " + input);

		input = new StringBuilder(input).reverse().toString();
		reverseBracket();

		System.out.println("    Перевернули строку");
		System.out.println("        " + input);

		System.out.println("Конвертируем в постфикс");
		InfToPost infToPost = new InfToPost(input);
		infToPost.createPostfix();
		output = new StringBuilder(infToPost.getPostfix());
		System.out.println("    Постфикс");
		System.out.println("        " + output);

		output = output.reverse();
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

	private void reverseBracket() {
		for (int i = 0; i < input.length(); i++) {
			String elem = Character.toString(input.charAt(i));
			StringBuilder temp = new StringBuilder(input);

			if (isCloseBracket(elem)) {
				input = temp.replace(i, i + 1, "(").toString();
			} else if (isOpenBracket(elem)) {
				input = temp.replace(i, i + 1, ")").toString();
			}
		}
	}

	private String getInput() {
		return input;
	}

	public String getPrefix() {
		return output.toString();
	}
}
