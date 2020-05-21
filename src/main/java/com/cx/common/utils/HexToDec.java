package com.cx.common.utils;

public class HexToDec {
	  //判断是否是16进制数  
	public static boolean IsHex(String strHex){  
	int i = 0;  
	if ( strHex.length() > 2 ){  
	if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){  
	i = 2;  
	}  
	}  
	for ( ; i<strHex.length(); ++i ){  
	char ch = strHex.charAt(i);  
	if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )  
	continue;  
	return false;  
	}  
	return true;  
	}  
	
	public static int HexToInt(String strHex){  
      int nResult = 0;  
      if ( !IsHex(strHex) )  
          return nResult;  
      String str = strHex.toUpperCase();  
      if ( str.length() > 2 ){  
          if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){  
              str = str.substring(2);  
          }  
      }  
      int nLen = str.length();  
      for ( int i=0; i<nLen; ++i ){  
          char ch = str.charAt(nLen-i-1);  
          try {  
              nResult += (GetHex(ch)*GetPower(16, i));  
          } catch (Exception e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
          }  
      }  
      return nResult;  
  }  
	
	 //计算16进制对应的数值  
  public static int GetHex(char ch) throws Exception{  
      if ( ch>='0' && ch<='9' )  
          return (int)(ch-'0');  
      if ( ch>='a' && ch<='f' )  
          return (int)(ch-'a'+10);  
      if ( ch>='A' && ch<='F' )  
          return (int)(ch-'A'+10);  
      throw new Exception("error param");  
  } 
	
  //计算幂  
  public static int GetPower(int nValue, int nCount) throws Exception{  
      if ( nCount <0 )  
          throw new Exception("nCount can't small than 1!");  
      if ( nCount == 0 )  
          return 1;  
      int nSum = 1;  
      for ( int i=0; i<nCount; ++i ){  
          nSum = nSum*nValue;  
      }  
      return nSum;  
  }
  /**
  /**
     * 16进制转bcd
     * 将16进制转成10进制，再将10进制转成bcd
     * @param hex 16进制数字String.valueOf(int number);这里忽略16进制的前缀0x，只转后面的数字为字符串类型，将16进制数字转成字符串传入此参数
     * @return bcd码
     */

    public static String HextoBcd(String hex){

        int decimal = Integer.parseInt(hex,16);

        String bcd = DecimaltoBcd(String.valueOf(decimal));

        return bcd;
    }
    public static String DecimaltoBcd(String str){
        String b_num="";
        for (int i = 0; i < str.length(); i++) {

            String b = Integer.toBinaryString(Integer.parseInt(str.valueOf(str.charAt(i))));

            int b_len=4-b.length();

            for(int j=0;j<b_len;j++){
                b="0"+b;
            }
            b_num+=b;
        }

        return b_num;
    }


    public static void main(String[] args) {
        System.out.println(HexToInt("FFFFFC18"));
    }
}
