package triangle;

import java.util.Scanner;

public class Triangle {

	private Integer side1;
	private Integer side2;
	private Integer side3;
	
	public Triangle(Integer side1, Integer side2, Integer side3) {
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
	}
	
	public String type() {
		if(side1.equals(side2) && side2.equals(side3)) 
			return "Equilatero";
		if(side1.equals(side2) || side1.equals(side3) || side2.equals(side3)) 
			return "Isosceles";
		return "Escaleno";
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); 
		
		System.out.println("Ingrese el valor del lado 1: ");
		Integer side1 = input.nextInt();
		System.out.println("Ingrese el valor del lado 2: ");
		Integer side2 = input.nextInt();
		System.out.println("Ingrese el valor del lado 3: ");
		Integer side3 = input.nextInt();
		
		Triangle triangle = new Triangle(side1, side2, side3);
		System.out.println("El triangulo ingresado es: " + triangle.type());
		
		input.close();
	}
	
	
}