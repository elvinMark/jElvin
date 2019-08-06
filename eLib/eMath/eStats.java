package eLib.eMath;

import java.util.*;
import java.io.*;

public class eStats{
	public static double avg(eVector data){
		double s = 0;
		for(int i=0;i<data.length;i++)
			s = s + data.data[i];
		return s/data.length;
	}
	public static double stdDev(eVector data){
		double s = 0;
		double av = avg(data);
		for(int i = 0;i<data.length;i++)
			s = s + (data.data[i] - av)*(data.data[i] - av);
		return Math.sqrt(s/data.length);
	}
	public static eVector linearRegression(eVector x, eVector y){
		eMatrix A;
		eVector b;
		double sx,sx2;
		double sxy,sy;
		A = new eMatrix(2,2);
		b = new eVector(2);
		sx = 0;
		sx2 = 0;
		sy = 0;
		sxy = 0;
		for(int i =0;i<x.length;i++){
			sx = sx + x.data[i];
			sx2 = sx2 + x.data[i]*x.data[i];
			sxy = sxy + x.data[i]*y.data[i];
			sy = sy + y.data[i];	
		}
		A.data[0].data[0] = sx2;
		A.data[0].data[1] = sx;
		A.data[1].data[0] = sx;
		A.data[1].data[1] = x.length;
		b.data[0] = sxy;
		b.data[1] = sy;
		return eMatrix.linearSystem(A,b);
	}
	public static double normalDistribution(double x,double u,double s){
		return (1/Math.sqrt(2*Math.PI*s*s))*Math.exp(-(x-u)*(x-u)/(2*s*s));
	}
	public static eVector normalDistribution(eVector x,double u,double s){
		eVector out;
		out = new eVector(x.length);
		for(int i = 0;i<x.length;i++)
			out.data[i] = normalDistribution(x.data[i],u,s);
		return out;
	}
	public static int factorial(int n){
		if (n==0 || n==1)
			return 1;
		return n*factorial(n-1);
	}
	public static double combinatory(double n,double k){
		if(n==k || k==0)
			return 1;
		return combinatory(n-1,k)+combinatory(n-1,k-1);
	}
	public static double binomialDistribution(double p,double x,int N){
		return combinatory(N,x)*Math.pow(p,x)*Math.pow(1-p,x-N);
	}
	public static eVector binomialDistribution(double p,eVector x,int N){
		eVector out;
		out = new eVector(x.length);
		for(int i = 0;i<x.length;i++)
			out.data[i] = binomialDistribution(p,x.data[i],N);
		return out;
	}
}