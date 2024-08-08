echo 'Running Build...'
./gradlew clean > log.txt
./gradlew build > log.txt
mv build/libs/name-1.0.jar ~/Desktop/code/minecraft/Server1.18.2/plugins/ > log.txt
cd ~/Desktop/code/minecraft/Server1.18.2/ > log.txt
echo 'Build Complete...'
#echo 'Starting Server...'
#java -jar ./server.jar --nogui
#echo 'Script Finished'