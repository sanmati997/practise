/* Decompiler 16ms, total 2020ms, lines 93 */
package com.scm.email.serviceprovider;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import joptsimple.internal.Strings;

public class commonUtility {
   public String readPropKey(String key) {
      Properties prop = null;

      try {
         String Path = (new File("src//main//resources//Config.properties")).getAbsolutePath();
         FileInputStream fis = new FileInputStream(new File(Path));
         prop = new Properties();
         prop.load(fis);
         return prop.getProperty(key);
      } catch (Exception var5) {
         System.out.println("Exception in reading Config.Propeties file" + var5.getMessage());
         return null;
      }
   }

   public Properties readProp() {
      Properties prop = null;

      try {
         String Path = (new File("src//main//resources//Config.properties")).getAbsolutePath();
         FileInputStream fis = new FileInputStream(new File(Path));
         prop = new Properties();
         prop.load(fis);
         return prop;
      } catch (Exception var7) {
         String Path = (new File("Config.properties")).getAbsolutePath();

         try {
            FileInputStream fis = new FileInputStream(new File(Path));
            prop = new Properties();
            prop.load(fis);
            return prop;
         } catch (Exception var6) {
            var6.printStackTrace();
            return null;
         }
      }
   }

   public String dateFormat(String strDate) {
      String formatedDate = null;

      try {
         if (strDate == null) {
            return "null";
         } else {
            String strDate1;
            SimpleDateFormat formatter;
            Date dateStr;
            if (strDate.toString().length() == 10) {
               strDate1 = strDate.toString();
               formatter = new SimpleDateFormat("yyyy-MM-dd");
               dateStr = formatter.parse(strDate1);
               formatter = new SimpleDateFormat("MM/dd/yyyy");
               formatedDate = formatter.format(dateStr);
               return "to_date('" + formatedDate + "','MM/dd/YYYY')";
            } else {
               strDate1 = strDate.toString();
               formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
               dateStr = formatter.parse(strDate1.toString());
               String formattedDate = formatter.format(dateStr);
               Date date1 = formatter.parse(formattedDate);
               formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
               formatter.format(date1);
               return "to_date('" + formatedDate + "','MM/dd/YYYY HH24:MI:SS')";
            }
         }
      } catch (Exception var8) {
         System.out.println("Exception in dateFormat() function || Date : " + strDate + " || Error : " + var8.getMessage());
         return null;
      }
   }

   public String toStringFormat(String strValue) {
      try {
         return Strings.isNullOrEmpty(strValue) ? null : "'" + strValue + "'";
      } catch (Exception var3) {
         System.out.println("Exception in toStringFormat() function || Value : " + strValue);
         return null;
      }
   }
}
