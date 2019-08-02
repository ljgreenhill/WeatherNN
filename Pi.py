from Adafruit_BME280 import *
from time import *
from datetime import *
import socket
import sys

sensor = BME280(t_mode=BME280_OSAMPLE_8, p_mode=BME280_OSAMPLE_8, h_mode=BME280_OSAMPLE_8)

minute = 0
while True:
    s = socket.socket()   
    host = '169.254.241.142'
    port = 12345               
    s.connect((host, port))
    sleep(2)
    minute = minute + 1;
    s.sendall("SENSORPI:" + str(minute)+ " " +  str(sensor.read_temperature()) + "-" 
    +str(minute)+ " " +  str(sensor.read_temperature_f()) + "-"
    +str(minute)+ " " +  str(sensor.read_pressure()) + "-"
    +str(minute)+ " " +  str(sensor.read_pressure_inches()) + "-"
    +str(minute)+ " " +  str(sensor.read_humidity()) + "-"
    +str(minute)+ " " +  str(sensor.read_dewpoint()) + "-"
    +str(minute)+ " " +  str(sensor.read_dewpoint_f()) + "-"
    )


    #data = str(s.recv(1024))
    #sprint(data)


    s.close()           
