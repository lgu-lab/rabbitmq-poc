package org.demo.tmp;

public class Student {

	private String firstName ;
	private String lastName ;
	private int    age ;
	
	private Student() {
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public static final class Builder {
		
		private String firstName = "";
		private String lastName = "";
		private int    age = 0;

		public Student build() {
			Student o = new Student();
			o.firstName = this.firstName;
			o.lastName = this.lastName;
			o.age = this.age;
	        return o;
		}
		
		public Builder firstName(String v) {
			this.firstName = v;
			return this;
		}
		public Builder lastName(String v) {
			this.lastName = v;
			return this;
		}
	};

}
