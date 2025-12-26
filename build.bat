@echo off
if not exist bin mkdir bin
javac -encoding UTF-8 --release 17 -cp "lib/*;src" -d bin src/model/*.java src/util/*.java src/dao/*.java src/frame/*.java 