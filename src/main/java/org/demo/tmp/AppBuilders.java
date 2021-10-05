package org.demo.tmp;

public class AppBuilders {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = new MyBuilder().a("aa").b("b").build();
		System.out.println("s = " +s);
		
		
		Student student = new Student.Builder().firstName("Bart").lastName("Simpsons").build();
		System.out.println("first name : " + student.getFirstName() );
		System.out.println("last name : " + student.getLastName() );
	}
	
	

}
