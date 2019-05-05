metadata {
    definition (name: "Clean Device",namespace: "BorrisTheCat", author: "Steven Stroud", importUrl: "https://raw.githubusercontent.com/borristhecat/HUBITAT/master/Fibaro%20UBS.groovy") {
   
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
