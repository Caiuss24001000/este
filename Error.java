/*
  Error.java
  Permite regresar al cliente REST un mensaje de error
  Carlos Pineda Guerrero, marzo 2022.
*/

public class Error
{
	String message;

	Error(String message)
	{
		this.message = message;
	}
}
