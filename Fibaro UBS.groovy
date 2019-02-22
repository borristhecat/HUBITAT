/*
  *  Device Type Definition File
  *
  *  Device Type:		Fibaro UBS - Dual Contact and Temperature Sensor
  *  File Name:			Fibaro UBS - Dual Contact and Temperature Sensor.groovy
  *	Initial Release:	2017-11-07
  *	Author:				Chris Charles modified by Borristhecat for hubitat and bit more mods by Dean Turner
  *	17/2/2019  rebuilt all logging, altered temperature creation and fixed typo bugs by Borristhecat
  *
  *  Copyright 2017 Chris Charles, based on original code by carlos.ir33, modified
  *  by Stuart Buchanan and Paul Crookes. Testing thanks to borristhecat.
  *
  ***************************************************************************************
  */
  
 metadata {
 	definition (name: "Fibaro UBS", namespace: "cjcharles0", author: "Chris Charles") {
     
    capability "Contact Sensor"
  	capability "Motion Sensor"
    capability "Sensor"
 	capability "Temperature Measurement"
    capability "Configuration"
 	//capability "Polling"
 	capability "Refresh"
     
    command "removeChildDevices"
    command "createChildDevices"
    command "createChildTempDevices"
	command "updateCurrentParams"
    command "listCurrentParams"
    command "open1"
    command "open2"
    command "close1"
    command "close2"
     
     attribute "contact1","enum",["open1","close1"]
     attribute "contact2","enum",["open2","close2"]
 	
 	fingerprint type: "2001", cc: "30 60 85 8E 72 70 86 7A", ccOut: "2B"
 }
 
 main(["temperature1"]) //, "contact1"
 details(["contact1","contact2",
 		"temp1text", "temperature1", "temp2text", "temperature2",
         "temp3text", "temperature3", "temp4text", "temperature4",
         "configure", "report", "createchildren", "createtempchildren", "removechildren"])
 
 preferences {
        //standard logging options
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
		input name: "settingEnable", type: "bool", title: "Enable setting", defaultValue: false
	 	input name: "Temps", type: "number", range: "0..4", required: true, defaultValue: "0",
            title: "Temperature probes number. \n" +
                   "Default value: 0."
	// input name: "Info", type: "paragraph", title:"Device Handler by @cjcharles", description: "Parameter Settings:", displayDuringSetup: false

    	   
       if (settingEnable) input name: "param1", type: "number", range: "0..65535", required: true, defaultValue: "0",
            title: "Parameter No. 1 - Input 1 Alarm Cancellation Delay. \n" +
                   "Additional delay after an alarm from input 1 has ceased.\n" +
                   "Time in seconds to delay the ceasing event.\n" +
                   "Default value: 0."
       
       if (settingEnable) input name: "param2", type: "number", range: "0..65535", required: true, defaultValue: "0",
            title: "Parameter No. 2 - Input 2 Alarm Cancellation Delay. \n" +
                   "Additional delay after an alarm from input 2 has ceased.\n" +
                   "Time in seconds to delay the ceasing event.\n" +
                   "Default value: 0."
       
       if (settingEnable) input name: "param3", type: "number", range: "0..3", required: true, defaultValue: "1",
            title: "Parameter No. 3 - Type of Input No 1." +
                   "Available settings:\n" +
                   "0 – INPUT_NO (Normal Open)\n" +
				   "1 – INPUT_NC (Normal Close)\n" +
				   "2 – INPUT_MONOSTABLE\n" +
				   "3 – INPUT_BISTABLE\n" +
                   "Default value: 1."

	if (settingEnable) input name: "param4", type: "number", range: "0..3", required: true, defaultValue: "1",
            title: "Parameter No. 4 - Type of Input No 2." +
                   "Available settings:\n" +
                   "0 – INPUT_NO (Normal Open)\n" +
				   "1 – INPUT_NC (Normal Close)\n" +
				   "2 – INPUT_MONOSTABLE\n" +
				   "3 – INPUT_BISTABLE\n" +
                   "Default value: 1."

	if (settingEnable) input name: "param5", type: "number", range: "0..255", required: true, defaultValue: "255",
            title: "Parameter No. 5 - Type of transmitted control or alarm frame for association group 1." +
                   "Available settings:\n" +
				   "0 – Frame ALARM GENERIC\n" +
				   "1 – Frame ALARM SMOKE\n" +
				   "2 – Frame ALARM CO\n" +
				   "3 – Frame ALARM CO2\n" +
				   "4 – Frame ALARM HEAT\n" +
				   "5 – Frame ALARM WATER\n" +
				   "255 – Control frame BASIC_SET\n" +
                   "Default value: 255."

	if (settingEnable) input name: "param6", type: "number", range: "0..255", required: true, defaultValue: "255",
            title: "Parameter No. 6 - Type of transmitted control or alarm frame for association group 2." +
                   "Available settings:\n" +
				   "0 – Frame ALARM GENERIC\n" +
				   "1 – Frame ALARM SMOKE\n" +
				   "2 – Frame ALARM CO\n" +
				   "3 – Frame ALARM CO2\n" +
				   "4 – Frame ALARM HEAT\n" +
				   "5 – Frame ALARM WATER\n" +
				   "255 – Control frame BASIC_SET\n" +
                   "Default value: 255."

	if (settingEnable) input name: "param7", type: "number", range: "0..255", required: true, defaultValue: "255",
            title: "Parameter No. 7 - Value of the parameter specifying the forced level of dimming / opening sun blinds when " +
            	   "sent a “switch on” / ”open” command from association group no. 1.\n" +
                   "Available settings:\n" +
                   "0-99 - Dimming or Opening Percentage\n" +
                   "255 - Last set percentage\n" +
                   "Default value: 255."

	if (settingEnable) input name: "param8", type: "number", range: "0..255", required: true, defaultValue: "255",
            title: "Parameter No. 8 - Value of the parameter specifying the forced level of dimming / opening sun blinds when " +
            	   "sent a “switch on” / ”open” command from association group no. 2.\n" +
                   "Available settings:\n" +
                   "0-99 - Dimming or Opening Percentage\n" +
                   "255 - Last set percentage\n" +
                   "Default value: 255."

	if (settingEnable) input name: "param9", type: "number", range: "0..3", required: true, defaultValue: "0",
            title: "Parameter No. 9 - Deactivating transmission of the frame cancelling the alarm or the " +
				   "control frame deactivating the device (Basic). Disable the alarm cancellation function.\n" +
                   "Available settings:\n" +
                   "0 – Cancellation sent for association group 1 and 2\n" +
				   "1 – Cancellation sent for association group 1 only\n" +
				   "2 – Cancellation sent for association group 2 only\n" +
				   "3 - Not sent for association group 1 or 2\n" +
                   "Default value: 0."

	if (settingEnable) input name: "param10", type: "number", range: "1..255", required: true, defaultValue: "20",
            title: "Parameter No. 10 - Interval between successive readings of temperature from all " +
				   "sensors connected to the device. (A reading does not result in sending to ST)\n" +
                   "Available settings:\n" +
                   "1-255 - Seconds between readings\n" +
                   "Default value: 20."

	if (settingEnable) input name: "param11", type: "number", range: "0..255", required: true, defaultValue: "200",
            title: "Parameter No. 11 - Interval between forcing to send report of the temperature. " +
				   "The forced report is sent immediately after the next temperature reading, " +
				   "irrespective of parameter 12. Advised to be 200 unless rapid temperature changes are expected.\n" +
                   "Available settings:\n" +
                   "0 - Deactivate temperature sending\n" +
                   "1-255 - Seconds between sends\n" +
                   "Default value: 200."

	if (settingEnable) input name: "param12", type: "number", range: "0..255", required: true, defaultValue: "8",
            title: "Parameter No. 12 - Insensitiveness to temperature changes. This is the maximum " +
				   "difference between the last reported temperature and the current temperature. " +
				   "If they differ by more than this then a report is sent.\n" +
                   "Available settings:\n" +
                   "0-255 - x/16 = temp diff in C\n" +
                   "x/80*9 = temp diff in F\n" +
                   "Default value: 8 (0.5oC)."

	if (settingEnable) input name: "param13", type: "number", range: "0..3", required: true, defaultValue: "0",
            title: "Parameter No. 13 - Transmitting the alarm or control frame in “broadcast” mode (i.e. to " +
				   "all devices within range), this information is not repeated by the mesh network." +
                   "Available settings:\n" +
                   "0 - IN1 and IN2 broadcast inactive,\n" +
                   "1 - IN1 broadcast mode active only,\n" +
                   "2 - IN2 broadcast mode active only,\n" +
                   "3 - INI and IN2 broadcast mode active.\n" +
                   "Default value: 0."

	if (settingEnable) input name: "param14", type: "number", range: "0..1", required: true, defaultValue: "0",
            title: "Parameter No. 14 - Scene activation functionality." +
                   "Available settings:\n" +
                   "0 - Deactivated functionality,\n" +
                   "1 - Activated functionality.\n" +
                   "Default value: 0."
 	} 
 }
	
def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def parse(String description) {
    if (logEnable) log.debug "parse description: ${description}"
    def cmd = zwave.parse(description,[ 0x26: 1])
    if (cmd) {zwaveEvent(cmd)}
    return
}

def refresh() {
 	def cmds = []
 	
  cmds << zwave.multiChannelV3.multiChannelCmdEncap(sourceEndPoint:1, destinationEndPoint:1, commandClass:32, command:2).format()
  cmds << zwave.multiChannelV3.multiChannelCmdEncap(sourceEndPoint:2, destinationEndPoint:2, commandClass:32, command:2).format()
// 	cmds << zwave.multiChannelV3.multiChannelCmdEncap(sourceEndPoint:3, destinationEndPoint:3, commandClass:49, command:5).format()
// 	cmds << zwave.multiChannelV3.multiChannelCmdEncap(sourceEndPoint:3, destinationEndPoint:4, commandClass:49, command:5).format()
 	delayBetween(cmds, 1500)
 	
 }
 
 def installed() {
 	if (txtEnable) log.info "installed()"
 }
 
 def uninstalled() {
    if (txtEnable) log.info "uninstalled()"
     removeChildDevices()
 }
 def configure() {
	if (txtEnable) log.info "configure()"
    updateCurrentParams()
}
 

 def createChildDevices(){
 	if (txtEnable) log.info "Adding Child Devices if not already added"
     for (i in 1..2) {
     	try {
         	if (txtEnable) log.info "Trying to create child switch if it doesn't already exist ${i}"
             def currentchild = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP${i}"}
             if (currentchild == null) {
            if (txtEnable) log.info "Creating child for IP${i}"
                 addChildDevice("hubitat", "Virtual Contact Sensor", "${device.deviceNetworkId}-IP${i}", [name: "${device.displayName} (Contact${i})", isComponent: false])
 			
             }
         } catch (e) {
         if (logEnable) log.debug "Error adding child ${i}: ${e}"
         }
     }
 }
 def createChildTempDevices() {
   	 switch(Temps){
		case 1:
		 	 for (i in 1) {
		if (txtEnable) log.info "Creating 1 Temperature child" 
                    def currentchild = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-TP${i}"}
              		if (currentchild == null) {
                    addChildDevice("hubitat", "Virtual Temperature Sensor", "${device.deviceNetworkId}-TP${i}", [name: "${device.displayName} (Temp${i})", isComponent: true])
					}
		}
		break
		case 2:
		 	for (i in 1..2) {
		if (txtEnable) log.info "Creating 2 Temperature child's" 
                    def currentchild = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-TP${i}"}
              		if (currentchild == null) {
                    addChildDevice("hubitat", "Virtual Temperature Sensor", "${device.deviceNetworkId}-TP${i}", [name: "${device.displayName} (Temp${i})", isComponent: true])
				}
				}
		 break
		 case 3:
		 	for (i in 1..3) {
		if (txtEnable) log.info "Creating 3 Temperature child's" 
                    def currentchild = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-TP${i}"}
              		if (currentchild == null) {
                    addChildDevice("hubitat", "Virtual Temperature Sensor", "${device.deviceNetworkId}-TP${i}", [name: "${device.displayName} (Temp${i})", isComponent: true])
				}
				}
		 break
		 case 4:
		 	for (i in 1..4) {
		if (txtEnable) log.info "Creating 4 Temperature child's" 
                    def currentchild = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-TP${i}"}
              		if (currentchild == null) {
                    addChildDevice("hubitat", "Virtual Temperature Sensor", "${device.deviceNetworkId}-TP${i}", [name: "${device.displayName} (Temp${i})", isComponent: true])
				}
				}
		 break
		 case 0:
		 	
		 if (txtEnable) log.info "Not creating any Temperature children as value is set to 0" 
           
		 break
	 }  	
 }
 private removeChildDevices() {
 	if (txtEnable) log.info "Removing Child Devices"
     try {
         getChildDevices()?.each {
         	try {
             	deleteChildDevice(it.deviceNetworkId)
             } catch (e) {
          log.debug "Error deleting ${it.deviceNetworkId}, probably locked into a SmartApp: ${e}"
             }
         }
     } catch (err) {
       log.debug "Either no children exist or error finding child devices for some reason: ${err}"
     }
 }

 def zwaveEvent(hubitat.zwave.commands.manufacturerspecificv1.ManufacturerSpecificReport cmd) {
  if (logEnable) log.debug("ManufacturerSpecificReport ${cmd.inspect()}")
 }
 
 def zwaveEvent(hubitat.zwave.commands.configurationv1.ConfigurationReport cmd) {
 	if (txtEnable) log.info("ConfigurationReport ${cmd.inspect()}")
 }
 
 def createEvent(hubitat.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd, Map item1) { 
 if (txtEnable)	log.info "manufacturerId:   ${cmd.manufacturerId}"
 if (txtEnable) log.info "manufacturerName: ${cmd.manufacturerName}"
 if (txtEnable) log.info "productId:        ${cmd.productId}"
 if (txtEnable) log.info "productTypeId:    ${cmd.productTypeId}"
 
 }
 
 def createEvent(hubitat.zwave.commands.versionv1.VersionReport cmd, Map item1) {	
     updateDataValue("applicationVersion", "${cmd.applicationVersion}")
if (txtEnable)	log.info "applicationVersion:      ${cmd.applicationVersion}"
if (txtEnable)	log.info "applicationSubVersion:   ${cmd.applicationSubVersion}"
if (txtEnable)	log.info "zWaveLibraryType:        ${cmd.zWaveLibraryType}"
if (txtEnable)	log.info "zWaveProtocolVersion:    ${cmd.zWaveProtocolVersion}"
if (txtEnable)	log.info "zWaveProtocolSubVersion: ${cmd.zWaveProtocolSubVersion}"
 }
 
 def zwaveEvent(hubitat.zwave.commands.basicv1.BasicSet cmd) {
if (logEnable) log.debug "BasicSet V1 ${cmd.inspect()}"
     def currentstate
     def motionstate
	if (cmd.value) {
 		currentstate = "open"
         motionstate = "inactive"
 	} else {
     	currentstate = "closed"
         motionstate = "active"
 	}
     createEvent(name: "contact${cmd.sourceEndPoint}", value: currentstate, descriptionText: "${device.displayName} is ${currentstate}")
     try {
         def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP${cmd.sourceEndPoint}"}
         if (childDevice)
         	childDevice.sendEvent(name: "motion", value: motionstate)
            childDevice.sendEvent(name: "contact", value: currentstate)
          if (txtEnable) log.info "Fibaro is ${currentstate}"
     } catch (e) {
         log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
     }
 }
 
 def zwaveEvent(hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap cmd) {
  if (logEnable) log.debug "ZWaveEvent V3 ${cmd.inspect()}"
 	def result
 	if (cmd.commandClass == 32) {
         def currentstate
         def motionstate
 		if (cmd.parameter == [0]) {
         	currentstate = "closed"
             motionstate = "active"
 		}
 		if (cmd.parameter == [255]) {
         	currentstate = "open"
             motionstate = "inactive"
 		}
     if (txtEnable) log.info "IP${cmd.sourceEndPoint} is ${currentstate}"
         //First update tile on this device
         sendEvent(name: "contact${cmd.sourceEndPoint}", value: currentstate, descriptionText: "$device.displayName - IP${cmd.sourceEndPoint} is ${currentstate}")
 		//If not null then we have found either IP1 or IP2, hence try to send to the child device aswell
         try {
             def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP${cmd.sourceEndPoint}"}
             if (childDevice)
                 childDevice.sendEvent(name: "motion", value: motionstate)
                 childDevice.sendEvent(name: "contact", value: currentstate)
         } catch (e) {
             log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
         }
     }
 	else if (cmd.commandClass == 49) {
 		if ((cmd.sourceEndPoint >= 3) && (cmd.sourceEndPoint <= 6)) { 
 			def tempsensorid = cmd.sourceEndPoint - 2
 			def tempendpoint = "TP" + tempsensorid.toString()
             def tempval = ((cmd.parameter[4] * 256) + cmd.parameter[5])
             if (tempval > 32767) {
             	//Here we deal with negative values
             	tempval = tempval - 65536
             }
             //Finally round the temperature
             
             def tempprocessed = (tempval / 100).toDouble().round(1)
             
             def units = getTemperatureScale()
 			//def cmdScale = cmd.scale == 1 ? "F" : "C"
 			//def tempval = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmdScale, cmd.precision).toDouble().round(1)
             
      if (txtEnable) log.info "${tempendpoint} has changed to ${tempprocessed}${units}"
             
             sendEvent(name: tempendpoint, value: tempprocessed, displayed: true, unit: getTemperatureScale)
             
 			//If not null then we have found either contact1 or contact2, hence try to send to the child
             try {
                 def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-${tempendpoint}"}
                 if (childDevice)
                 	//We found a child device that matches so send it the new temperature
                     childDevice.sendEvent(name: "temperature", value: tempprocessed)
             } catch (e) {
             	//Not an error message here as people may not want child temperature devices
         if (logEnable) log.debug "Couldn't find child ${tempendpoint} device, probably doesn't exist...? Error: ${e}"
             }
         }
 	}
 	else {
 		//Send them here just in case we want to do more complicated processing (not doing it as need to have endpoint passed and that makes it a bit messy)
 		def encapsulatedCommand = cmd.encapsulatedCommand([0x31: 2, 0x60: 3, 0x85: 2, 0x8E: 2, 0x72: 1, 0x70: 1, 0x86: 1, 0x7A: 1, 0xEF: 1, 0x2B: 1]) // can specify command class versions here like in zwave.parse
 	if (logEnable) log.debug ("Command from endpoint ${cmd.sourceEndPoint}: ${encapsulatedCommand}")
 		if (encapsulatedCommand) {
 			result = zwaveEvent(encapsulatedCommand)
 		}
 	}
     return result
 }
 
 def zwaveEvent(hubitat.zwave.commands.sensormultilevelv2.SensorMultilevelReport cmd)
 {
 	//This is no longer used as caught in an earlier event, but kept here in case the code is useful
 if (logEnable) log.debug "Sensor MultiLevel Report - Sensor Type = ${cmd.sensorType}"
 	switch (cmd.sensorType) {
 		case 1:
 			// temperature
 			def cmdScale = cmd.scale == 1 ? "F" : "C"
 			def tempval = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmdScale, cmd.precision).toDouble().round(1)
             sendEvent(name: "TP1", value: tempval, displayed: false) //unit: getTemperatureScale()
 			break;
 	}
  if (logEnable) log.debug map
 	createEvent(map)
 }
 
 def zwaveEvent(hubitat.zwave.commands.sensormultilevelv1.SensorMultilevelReport cmd) {
   if (logEnable) log.debug "SensorMultilevelReport $cmd"
 }
 
 def zwaveEvent(hubitat.zwave.Command cmd) {
 	// This will capture any commands not handled by other instances of zwaveEvent
 	// and is recommended for development so you can see every command the device sends
 if (logEnable)	log.debug "Catchall reached for cmd: ${cmd.toString()}}"
 if (txtEnable)	return createEvent(descriptionText: "${device.displayName}: ${cmd}")
 }

	def updateCurrentParams() {
	if (txtEnable) log.info "Sending configuration parameters to device"
    def cmds = []
	cmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:2, nodeId:[zwaveHubNodeId]).format()
	cmds << zwave.associationV2.associationSet(groupingIdentifier:3, nodeId:[zwaveHubNodeId]).format()
	cmds << zwave.associationV1.associationRemove(groupingIdentifier:1, nodeId:zwaveHubNodeId).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 1, configurationValue:[param1.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 2, configurationValue:[param2.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 3, configurationValue:[param3.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 4, configurationValue:[param4.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 5, configurationValue:[param5.value]).format()
    cmds << zwave.configurationV1.configurationSet(parameterNumber: 6, configurationValue:[param6.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 7, configurationValue:[param7.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 8, configurationValue:[param8.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 9, configurationValue:[param9.value]).format()
    cmds << zwave.configurationV1.configurationSet(parameterNumber: 10, configurationValue:[param10.value]).format()
    cmds << zwave.configurationV1.configurationSet(parameterNumber: 11, configurationValue:[param11.value]).format()
    cmds << zwave.configurationV1.configurationSet(parameterNumber: 12, configurationValue:[param12.value]).format()
	cmds << zwave.configurationV1.configurationSet(parameterNumber: 13, configurationValue:[param13.value]).format()
    cmds << zwave.configurationV1.configurationSet(parameterNumber: 14, configurationValue:[param14.value]).format()
	delayBetween(cmds, 500)
}
 
 def listCurrentParams() {
 if (txtEnable) log.info "Listing of current parameter settings of ${device.displayName}"
 def cmds = []
 	cmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier:2).format()
 	cmds << zwave.associationV2.associationGet(groupingIdentifier: 3).format()
 	cmds << zwave.associationV1.associationGet(groupingIdentifier: 1).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 1).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 2).format()
    cmds << zwave.configurationV1.configurationGet(parameterNumber: 3).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 4).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 5).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 6).format()
    cmds << zwave.configurationV1.configurationGet(parameterNumber: 7).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 8).format()
    cmds << zwave.configurationV1.configurationGet(parameterNumber: 9).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 10).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 11).format()
    cmds << zwave.configurationV1.configurationGet(parameterNumber: 12).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 13).format()
 	cmds << zwave.configurationV1.configurationGet(parameterNumber: 14).format()
 	delayBetween(cmds, 500)
 }
 
 def open1() {
     sendEvent(name: "contact1", value: "open", descriptionText: "$device.displayName (1) is opened manually")
     try {
         def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP1"}
         log.info "Changing child ${childDevice} to open/inactive"
         if (childDevie)
         	childDevice.sendEvent(name: "motion", value: "inactive")
            childDevice.sendEvent(name: "contact", value: "open")
     } catch (e) {
         log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
     }
 }
 
 def close1() {
     sendEvent(name: "contact1", value: "closed", descriptionText: "$device.displayName (1) is closed manually")
     try {
         def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP1"}
         log.info "Changing child ${childDevice} to closed/active"
         if (childDevice)
         	childDevice.sendEvent(name: "motion", value: "active")
            childDevice.sendEvent(name: "contact", value: "closed")
     } catch (e) {
         log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
     }
 }
 
 def open2() {
     sendEvent(name: "contact2", value: "open", descriptionText: "$device.displayName (2) is opened manually")
     try {
         def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP2"}
         log.info "Changing child ${childDevice} to open/inactive"
         if (childDevice)
         	childDevice.sendEvent(name: "motion", value: "inactive")
            childDevice.sendEvent(name: "contact", value: "open")
     } catch (e) {
         log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
     }
 }
 
 def close2() {
     sendEvent(name: "contact2", value: "closed", descriptionText: "$device.displayName (2) is closed manually")
     try {
         def childDevice = getChildDevices()?.find { it.deviceNetworkId == "${device.deviceNetworkId}-IP2"}
         log.info "Changing child ${childDevice} to closed/active"
         if (childDevice)
         	childDevice.sendEvent(name: "motion", value: "active")
 			childDevice.sendEvent(name: "contact", value: "closed")
     } catch (e) {
         log.error "Couldn't find child device, probably doesn't exist...? Error: ${e}"
	 }
 }

//capture preference changes
def updated() {
	log.info "updated..."
	log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
	log.warn "Settings is: ${settingEnable == true}"
	if (logEnable) runIn(1800,logsOff)
	if (settingEnable) runIn(1800,logsOff)

    def cmds = []
}
