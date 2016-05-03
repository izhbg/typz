package com.izhbg.typz.base.util;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据计算公式
 * @author yangzc
 *
 */
public class MathEval {

	public static char PLUS = '+';
	public static char MINUS = '-';
	public static char MULTI = '*';
	public static char DEVIDE = '/';
	
	public static char BRACKET_LEFT = '(';
	public static char BRACKET_RIGHT = ')';
	
	/**
	 * 计算带小括号的公式
	 * @param line
	 * @return
	 */
	public static double eval(String line){
		while(line.indexOf(BRACKET_LEFT) != -1){
			Pattern pattern = Pattern.compile("\\(([^\\(\\)]*?)\\)");
			Matcher matcher = pattern.matcher(line);
			while(matcher.find()){
				double result = simpleEval(matcher.group(1));
				line = line.replace(matcher.group(), result+"");
			}
		}
		return simpleEval(line);
	}
	
	/**
	 * 计算不带括号的公式
	 * @param line
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static double simpleEval(String line){
		Stack<Double> valueStack = new Stack<Double>();//保存值的堆栈
		Stack<Character> markStack = new Stack<Character>();//保存符号的堆栈
		
		char ch[] = line.toCharArray();
		
		//计算乘除操作
		String tmpValue = "";
		boolean isOper = false;
		for(int i=0; i< ch.length; i++){
			if( ch[i] == PLUS || ch[i] == MINUS || ch[i] == MULTI || ch[i] == DEVIDE) {
				double dv = Double.valueOf(tmpValue).doubleValue();
				if(isOper){
					double dv1 = valueStack.pop();
					char op = markStack.pop();
					double result = simpleTwoEval(op, dv1, dv);
					dv = result;
				}
				valueStack.push(dv);
				markStack.push(ch[i]);
				tmpValue = "";
				isOper = false;
				if( ch[i] == MULTI || ch[i] == DEVIDE )
					isOper = true;
			}else{
				tmpValue += ch[i] + "";
				
				if(i == ch.length -1){
					double dv = Double.valueOf(tmpValue).doubleValue();
					if(isOper){
						double dv1 = valueStack.pop();
						char op = markStack.pop();
						double result = simpleTwoEval(op, dv1, dv);
						dv = result;
					}
					valueStack.push(dv);
				}
			}
		}
//		for(int i=0; i< valueStack.size(); i++){
//			System.out.println(valueStack.get(i));
//		}
//		for(int i=0; i< markStack.size(); i++){
//			System.out.println(markStack.get(i));
//		}
		
		//计算加减操作
		valueStack = (Stack<Double>) reverseStack(valueStack);
		markStack = (Stack<Character>) reverseStack(markStack);
		while(valueStack.size() > 1){
			double v1 = valueStack.pop();
			double v2 = valueStack.pop();
			char op = markStack.pop();
			double result = simpleTwoEval(op, v1, v2);
			valueStack.push(result);
		}
		return valueStack.get(0);
	}
	
	/**
	 * 把整个堆栈翻转
	 * @param stack
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Stack<?> reverseStack(Stack<?> stack){
		Stack reverse = new Stack();
		int stackSize = stack.size();
		for(int i=0; i< stackSize; i++){
			reverse.push(stack.pop());
		}
		return reverse;
	}
	
	/**
	 * 只计算简单的两个数结果
	 * @param op
	 * @param value1
	 * @param value2
	 * @return
	 */
	private static double simpleTwoEval(char op, double value1, double value2){
		if(op == PLUS){
			return value1 + value2;
		}else if(op == MINUS){
			return value1 - value2;
		}else if(op == MULTI){
			return value1 * value2;
		}else if(op == DEVIDE){
			return value1 / value2;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		double result = MathEval.eval("1+(2*(3+2))-6+(3/2)+4/2");
		//System.out.println();
		//System.out.println(result);
		
		 String li = "c[1]r[4]+c[1]r[5]+c[1]r[62]+c[1]r[7]";
		 String[] nums = li.split("\\D+");
		 String temp = null;
		for(int i=1;i<nums.length-1;i=i+2){
			temp = "c\\["+nums[i]+"\\]r\\["+nums[i+1]+"\\]";
			li = li.replaceAll(temp, "2");
		}
		System.out.println(li);
	}
}

