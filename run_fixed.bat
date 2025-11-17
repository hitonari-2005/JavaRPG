@echo off
REM UTF-8エンコーディングを設定
chcp 65001 > nul

REM カレントディレクトリをバッチファイルの場所に設定
cd /d "%~dp0"

REM Javaプログラムを実行（UTF-8エンコーディングを指定）
java -Dfile.encoding=UTF-8 javaRPG.Main

REM 実行後、キー入力待ち
pause
