package main

import (
	"fmt"

	"gofun.com/greetings"
)

func main() {
	message := greetings.Hello("Twitch")
	fmt.Println(message)
}
