@echo off
chcp 65001 > nul
pushd "\\192.168.100.5\studentsprofiles$\JR2418\Desktop\JavaRPG"

echo Compiling Java files...

"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\AllyUnit\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\BattleUnit\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\EnemyUnit\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\Interface\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\battle\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\item\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\magic\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\merchant\*.java
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\javac.exe" -encoding UTF-8 javaRPG\*.java

echo Compilation complete!

popd
pause
