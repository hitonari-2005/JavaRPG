@echo off
REM コンパイル用バッチファイル
chcp 65001 > nul
cd /d "%~dp0"

echo ===== Javaファイルをコンパイル中... =====
javac -encoding UTF-8 javaRPG/**/*.java javaRPG/*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===== コンパイル成功！ =====
    echo.
    echo run_fixed.bat をダブルクリックして実行してください。
) else (
    echo.
    echo ===== コンパイルエラーが発生しました =====
)

pause
