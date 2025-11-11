@echo off
pushd "\\192.168.100.5\studentsprofiles$\JR2418\Desktop\JavaRPG"
echo Compiling Java files...
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/Character/Character.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/Interface/Battlable.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/BattleUnit/BattleUnit.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/AllyUnit/AllyUnit.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/Interface/IEquipSword.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/AllyUnit/Hero.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/EnemyUnit/EnemyUnit.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/item/Item.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/item/Sword.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/item/Staff.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/merchant/CommonMerchant.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" javaRPG/Main.java
echo Compilation complete!
echo.
echo Running program...
echo.
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\java.exe" javaRPG.Main
popd
pause
