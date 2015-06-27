package com.unlam.uno.code;

import java.util.*;

/** Traduce un numero a su equivalente en letras
 * 
 */
public class NumberDescription 
{	
	private int flag = 0; // indica caso especial en donde la traduccion no es directa

	/** Convierte un numero de un digito en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	private String unidad(int numero){
		// se crea un vector que nos permitira obtener el string
		// en base al indice
		Vector< String > unidad = new Vector< String >();
		unidad.add( "" );
		unidad.add( "uno" );
		unidad.add( "dos" );
		unidad.add( "tres" );
		unidad.add( "cuatro" );
		unidad.add( "cinco" );
		unidad.add( "seis" );
		unidad.add( "siete" );
		unidad.add( "ocho" );
		unidad.add( "nueve" );
		
		// se controla el caso especual de 1 
		if (flag == 1 && numero == 1)
			return "un";
		
		// se devuelve su equivalente en letras
		return unidad.get( numero );
	}
	
	/** Convierte un numero de dos digitoss ( o menor ) en su equivalente en letras
	 * Nota: No hay control si el numero es mayor a dos digitos
	 * 
	 * @param numero
	 * @return String
	 */
	private String decena(int numero){
	
		String num_letra;

		// se controla si el numero esta entre el rango 10 a 99 
		if (numero >= 10 && numero <= 99)
		{
			// se crea un vector que nos permitira obtener el string
			// en base al indice
			Vector< String > decena = new Vector< String >();
			decena.add( "" ); // este indice no se utiliza pero es necesario para la
                              // estructura del vector a crear
			decena.add( "diez " );
			decena.add( "veinte " );
			decena.add( "treinta " );
			decena.add( "cuarenta " );
			decena.add( "cincuenta " );
			decena.add( "sesenta " );
			decena.add( "setenta " );
			decena.add( "ochenta " );
			decena.add( "noventa " );
			
			// se obtiene unicamente el numero de la decena para utilizarlo
			// como indice
			Integer dec = numero/10;
			num_letra = decena.get( dec );
			
			// si el numero no termina en cero, entonces analizamos el siguiente digito
			if( numero - (dec*10) > 0)
			{  
				// En caso de ser 1X
				if( dec == 1 )
			    {
					// se crea un vector que nos permitira obtener el string
					// en base al indice
					Vector< String > tmp = new Vector< String >();
					tmp.add( "diez " );
					tmp.add( "once " );
					tmp.add( "doce " );
					tmp.add( "trece " );
					tmp.add( "catorce " );
					tmp.add( "cincuenta " );
					tmp.add( "dieciseis " );
					tmp.add( "diecisiete " );
					tmp.add( "dieciocho " );
					tmp.add( "diecinueve " );
					
					Integer indice = numero - (dec*10); 
				    num_letra = tmp.get( indice );
			    }
				// En caso de ser 2X
				else if( dec == 2  )
				{
				  num_letra = "veinti".concat( unidad(numero - (dec*10) ) );
				}
				// Otros casos XX los cuales no es necesario un analisis mas 
				else
				{
				  num_letra = num_letra.concat("y ").concat( unidad( numero - (dec*10) ));
				}
			}	
		}
		// el rango se encuetra entre 0-9
		else 
		{
			num_letra = unidad(numero);	
		}
		
	    return num_letra;
	}
	
	/** Convierte un numero de tres digitos ( o menor ) en su equivalente en letras
	 *  Nota: No hay control si el numero es mayor a tres digitos
	 *  
	 * @param numero
	 * @return
	 */
	private String centena(int numero){

		String num_letra = "";
		
		// Se controla que el rango sea mayor a 100
		if (numero >= 100)
		{
			// se crea un vector que nos permitira obtener el string
			// en base al indice
			Vector< String > centena = new Vector< String >();
			centena.add( "" ); // este indice no se utiliza pero es necesario para la
			                   // estructura del vector a crear
			centena.add( "cien " );
			centena.add( "doscientos " );
			centena.add( "trescientos " );
			centena.add( "cuatrocientos " );
			centena.add( "quinientos " );
			centena.add( "seiscientos " );
			centena.add( "setecientos " );
			centena.add( "ochocientos " );
			centena.add( "novecientos " );
			
			// se obtiene el tercer digito
			Integer cen = numero/100;
			num_letra = centena.get( cen );
		
			// se controla si es necesario analizar el siguiente digito
			if( numero - (cen*100) > 0)
			{  	
				// caso 1XX 
				if (cen == 1)
					num_letra = "ciento ".concat(decena(numero - (cen*100) ));
				else // caso XXX
					num_letra = num_letra.concat(decena(numero - (cen*100) ));
			}
		}
		// el rango es menor a 100
		else
		{
			num_letra = decena(numero);
		}
		
		return num_letra;	
	}	

	/** Convierte un numero de cuatro digitos ( o menor ) en su equivalente en letras
	 *  Nota: No hay control si el numero es mayor a cuatro digitos
	 * 
	 * @param numero
	 * @return
	 */
	private String miles(int numero)
	{
		String num_letram = "";
		
		// se analiza rango 1000 a 2000
		if (numero >= 1000 && numero <2000)
		{
			num_letram = ("mil ").concat(centena(numero%1000));
		}
		// se analiza rango 2000 a 10000
		else if (numero >= 2000 && numero <10000)
		{
			flag=1;
			num_letram = unidad(numero/1000).concat(" mil ").concat(centena(numero%1000));
		}
		// se analiza rango menor a 1000
		else if (numero < 1000)
			num_letram = centena(numero);
		
		return num_letram;
	}		

	/** Convierte un numero de cinco digitos ( o menor ) en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	private String decmiles(int numero){
		String num_letradm="";

		if (numero == 10000)
		{
			num_letradm = "diez mil";
		}
		// se analiza rango 10000 a 20000
		else if (numero > 10000 && numero <20000){
			flag=1;
			num_letradm = decena(numero/1000).concat("mil ").concat(centena(numero%1000));		
		}
		// se analiza rango 20000 a 100000
		else if (numero >= 20000 && numero <100000){
			flag=1;
			num_letradm = decena(numero/1000).concat(" mil ").concat(miles(numero%1000));		
		}
		// se analiza rango menor a 10000
		else if (numero < 10000)
			num_letradm = miles(numero);
		
		return num_letradm;
	}		

	/** Convierte un numero de seis digitos ( o menor ) en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	private String cienmiles(int numero){
		
		String num_letracm = "";
		if (numero == 100000)
		{
			num_letracm = "cien mil";
		}
		// se analiza rango 100000 a 1000000
		else if (numero >= 100000 && numero <1000000)
		{
			flag=1;
			num_letracm = centena(numero/1000).concat(" mil ").concat(centena(numero%1000));		
		}
		// se analiza rango menor a 100000
		else if (numero < 100000)
			num_letracm = decmiles(numero);
		return num_letracm;
	}		

	/** Convierte un numero de siete digitos ( o menor ) en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	private String millon(int numero){
		String num_letramm = "";

		// se analiza rango 1000000 a 2000000
		if (numero >= 1000000 && numero <2000000){
			flag=1;
			num_letramm = ("Un millon ").concat(cienmiles(numero%1000000));
		}
		// se analiza rango 2000000 a 10000000
		if (numero >= 2000000 && numero <10000000){
			flag=1;
			num_letramm = unidad(numero/1000000).concat(" millones ").concat(cienmiles(numero%1000000));
		}
		// se analiza rango menor a 1000000
		if (numero < 1000000)
			num_letramm = cienmiles(numero);
		
		return num_letramm;
	}		
	
	
	/** Convierte un numero de ocho digitos ( o menor ) en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	private String decmillon(int numero)
	{
		String num_letradmm = "";
		
		if (numero == 10000000)
			num_letradmm = "diez millones";
		// se analiza rango 10000000 a 20000000
		if (numero > 10000000 && numero <20000000)
		{
			flag=1;
			num_letradmm = decena(numero/1000000).concat("millones ").concat(cienmiles(numero%1000000));		
		}
		// se analiza rango 20000000 a 100000000
		if (numero >= 20000000 && numero <100000000)
		{
			flag=1;
			num_letradmm = decena(numero/1000000).concat(" milllones ").concat(millon(numero%1000000));		
		}

		// se analiza rango menor a 10000000
		if (numero < 10000000)
			num_letradmm = millon(numero);
		
		return num_letradmm;
	}		

	/** Convierte un numero de ocho digitos ( o menor ) en su equivalente en letras
	 * 
	 * @param numero
	 * @return
	 */
	public String convertirLetras(int numero)
	{	
		// Para el caso de cero
		if( numero == 0)
			return "cero";
		
		return decmillon(numero);
	} 	
}



