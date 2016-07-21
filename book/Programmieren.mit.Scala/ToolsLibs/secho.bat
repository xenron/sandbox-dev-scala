::#!
@echo off
call scala %0 %*
goto :eof
::!#
print("Sie haben eingegeben: ")
argv.toList foreach { s => printf("%s ", s) }
println
