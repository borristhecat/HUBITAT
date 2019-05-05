metadata {
    definition (name: "Clean Device",namespace: "BorrisTheCat", author: "Steven Stroud") {
   
		//capability "Configuration"
		command    "WipeState"
		command    "ClearSchedule"


		
    }
}

//def configure() {
//	WipeState()
//}

def WipeState() {
	log.warn "wiping states"
	state.clear()
}
def updated() {
	log.info "updated()"
	
} 

def ClearSchedule(){
	log.warn "Clearing and schedules"
	unschedule()
	
}
