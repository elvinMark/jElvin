package eLib.eMath;

import java.io.*;
import java.util.*;

public class eComplex{
	public double r;
	public double i;
	
	public eComplex(double real,double img){
		this.r = real;
		this.i = img;
	}
	public void print(){
		System.out.println(this.r + "+" + this.i + "i");
	}
	//Basic operations
	public eComplex add(eComplex c1){
		eComplex out = new eComplex(0,0);
		out.r = this.r + c1.r;
		out.i = this.i + c1.i;
		return out;
	}
	public eComplex diff(eComplex c1){
		eComplex out = new eComplex(0,0);
		out.r = this.r - c1.r;
		out.i = this.i - c1.i;
		return out;
	}
	public eComplex times(eComplex c1){
		eComplex out = new eComplex(0,0);
		out.r = this.r*c1.r - this.i*c1.i;
		out.i = this.r*c1.i + this.i*c1.r;
		return out;
	}
	public eComplex conjugate(){
		eComplex out = new eComplex(0,0);
		out.r = this.r;
		out.i = - this.i;
		return out;
	}
	public static eComplex exp(eComplex c){
		eComplex out = new eComplex(0,0);
		out.r = Math.exp(c.r)*Math.cos(c.i);
		out.i = Math.exp(c.r)*Math.sin(c.i);
		return out;
	}
	public double norm(){
		return Math.sqrt(this.r*this.r + this.i*this.i);
	}
	public static eComplex[] fft(eComplex[] data){
		eComplex[] out;
		eComplex[] data_even;
		eComplex[] data_odd;
		eComplex[] fft_even;
		eComplex[] fft_odd;
		eComplex aux;
		int i,j;
		aux = new eComplex(0,0);
		out = new eComplex[data.length];
		if(data.length == 1){
			out[0] = new eComplex(data[0].r,data[0].i);
			return out;
		}
 		data_even = new eComplex[data.length/2];
 		data_odd = new eComplex[data.length/2];
 		for(i=0;i<data.length/2;i++){
 			data_even[i] = data[2*i];
 			data_odd[i] = data[2*i+1];
 		}
 		fft_even = eComplex.fft(data_even);
 		fft_odd = eComplex.fft(data_odd);
 		for(i=0;i<data.length;i++){
 			aux.i = 2*Math.PI*i/(data.length);
 			out[i] = fft_even[i%(data.length/2)].add(eComplex.exp(aux).times(fft_odd[i%(data.length/2)]));
 		}
		return out;
	}
}