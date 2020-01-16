metadata {
    definition (name: "Clean Device",namespace: "BorrisTheCat", author: "Steven Stroud", importUrl: "https://raw.githubusercontent.com/borristhecat/HUBITAT/master/Fibaro%20UBS.groovy") {
   
		//capability "Configuration"
		command    "WipeState"
		command    "ClearSchedule"
        command "removeChildDevices"


		
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
 private removeChildDevices() {
 	log.info "Removing Child Devices, if any installed"
     try {
         getChildDevices()?.each {
         	try {
             	deleteChildDevice(it.deviceNetworkId)
             } catch (e) {
          log.debug "Error deleting ${it.deviceNetworkId}, probably locked in a App: ${e}"
             }
         }
     } catch (err) {
       log.debug "Either no children exist or error finding child devices for some reason: ${err}"
     }
 }
