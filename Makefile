all:
	javac -d ./monopolyApp ./src/edu/monopoly/exceptions/*.java \
	./src/edu/monopoly/game/actors/*.java \
	./src/edu/monopoly/game/board/cells/*.java \
	./src/edu/monopoly/game/board/*.java \
	./src/edu/monopoly/game/commands/*.java \
	./src/edu/monopoly/game/*.java \
	./src/edu/monopoly/io/*.java \
	./src/edu/monopoly/app/*.java

clean:
	rm -rf ./monopolyApp/edu

run:
	cd ./monopolyApp && java edu.monopoly.app.Main
