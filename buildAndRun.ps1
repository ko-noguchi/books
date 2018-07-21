./gradlew distZip
pushd build/distributions
Expand-Archive -Force books.zip
pushd books/books/bin
if (!(Test-Path registerer.dat)) {
    Copy-Item ../../../../../registerer.dat .
}
./books.bat
popd
popd
