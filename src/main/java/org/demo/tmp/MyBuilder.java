package org.demo.tmp;

public final class MyBuilder {

	private String a ;
	private String b ;
	private String c ;
	
	public MyBuilder() {
	};

	public String build() {
        return new String(a + "/" + b + "/" +c);
	}
	
	public MyBuilder a(String a) {
		this.a = a;
		return this;
	}
	public MyBuilder b(String b) {
		this.b = b;
		return this;
	}
	public MyBuilder c(String c) {
		this.c = c;
		return this;
	}
}
