package eLib.eMath;

import java.util.*;
import java.io.*;

public class eVector{
	public int length;
	public double data[];
	
	public eVector(int length){
		this.length = length;
		this.data = new double[length];
	}
	public eVector(String data,int length){
		StringTokenizer st;
		String token;
		this.length = length;
		this.data = new double[length];
		st = new StringTokenizer(data);
		for(int i = 0;i<this.length;i++){
			token = st.nextToken();
			this.data[i] = Double.parseDouble(token);
		}
	}
	public eVector(double[] data,int length){
		this.length = length;
		this.data = data;
	}
	public void print(){
		for(int i = 0;i<this.length;i++){
			System.out.print(this.data[i] + " ");
		}
		System.out.println("");
	}
	public void random(){
		Random rand = new Random();
		for(int i = 0;i<this.length;i++)
			this.data[i] = rand.nextDouble(); 		
	}
	public void zeros(){
		for(int i = 0;i<this.length;i++)
			this.data[i] = 0;	
	}
	public void ones(){
		for(int i = 0;i<this.length;i++)
			this.data[i] = 1;
	}
	//Basic Operations
	public eVector add(eVector v1){
		eVector out = new eVector(this.length);
		for(int i = 0;i<this.length;i++)
			out.data[i] = this.data[i] + v1.data[i];
		return out;
	}
	public eVector diff(eVector v1){
		eVector out = new eVector(this.length);
		for(int i = 0;i<this.length;i++)
			out.data[i] = this.data[i] - v1.data[i];
		return out;
	}
	public eVector times(double num){
		eVector out = new eVector(this.length);
		for(int i = 0;i<this.length;i++)
			out.data[i] = num*this.data[i];
		return out;
	}
	public eVector times(eVector v1){
		eVector out = new eVector(this.length);
		for(int i = 0;i<this.length;i++)
			out.data[i] = this.data[i] * v1.data[i];
		return out;	
	}
	public double dot(eVector v1){
		double s = 0;
		for(int i = 0;i<this.length;i++)
			s = s + this.data[i]*v1.data[i];
		return s;
	}
	public double norm(){
		double s = 0;
		for(int i = 0;i<this.length;i++)
			s = s + this.data[i]*this.data[i];
		s = Math.sqrt(s);
		return s;	
	}
	public eVector proj(eVector u){
		return u.times(this.dot(u));
	}
	public void normalize(){
		double n;
		n = this.norm();
		for(int i=0;i<this.length;i++)
			this.data[i] = this.data[i]/n;
	}
	//Saving and Loading
	public void load(String dir){
		try{
			StringTokenizer st;
			File p = new File(dir);
			Scanner scan = new Scanner(p);
			int i=0;
			String line = scan.nextLine();
			st = new StringTokenizer(line);
			while(st.hasMoreTokens()){
				this.data[i] = Double.parseDouble(st.nextToken());
				i++;
			}
			scan.close();
		}
		catch(Exception e){}
	}
	public void save(String dir){
		try{
			File p = new File(dir);
			FileWriter writer = new FileWriter(p);
			for(int i = 0;i<this.length;i++)
				writer.write(this.data[i] + " ");
			writer.close();
		}
		catch(Exception e){}
	}
}