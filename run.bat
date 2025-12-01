@echo off
chcp 65001 > nul
pushd "\\192.168.100.5\studentsprofiles$\JR2418\Desktop\JavaRPG"
"\\192.168.100.5\studentsprofiles$\JR2418\Desktop\oracleJdk-25\bin\java.exe" -Dfile.encoding=UTF-8 javaRPG.Main
popd
pause
