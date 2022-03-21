public class Solution {
	public static void main(String[] args) {
		String input = "(a + b) / (c + d - 1)";
		//String input = "(a+3)*5-(2*b)";
		//String input = "1 + (2 * 3) / 4 - 5";
		//String input = "a+b/(c-d)";

		System.out.println("Постфиксная запись:");
		InfToPost infToPost = new InfToPost(input);
		infToPost.createPostfix();
		System.out.println("Постфикс");
		System.out.println("    " + infToPost.getPostfix());

		System.out.println();

		System.out.println("Префиксная запись:");
		InfToPref infToPref = new InfToPref(input);
		infToPref.createPrefix();
		System.out.println("Префикс");
		System.out.println("    " + infToPref.getPrefix());
	}
}
