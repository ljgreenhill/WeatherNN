import socket
import threading
from time import*
import matlab.engine

print("Server is running")

open('tc.log','w').close()
open('tf.log','w').close()
open('p.log','w').close()
open('pi.log','w').close()
open('h.log','w').close()
open('dc.log','w').close()
open('df.log','w').close()

class ThreadedServer(object):
    def __init__(self, host, port):
        self.host = host
        self.port = port
        print("HOST: 169.154.241.142")
        print("PORT: " + str(port))
        print("-----------------------------")
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.sock.bind((self.host, self.port))
        print("Server is listening")

    def listen(self):
        self.sock.listen(5)
        while True:
            client, address = self.sock.accept()
            client.settimeout(120)
            threading.Thread(target = self.listenToClient,args = (client,address)).start()

    def listenToClient(self, client, address):
        while True:
            
                data = str(client.recv(1024))
                if "PI" in data:
                    
            
                    print("Connected to Pi")
                    newData = data.strip()
                    n = newData.split(":")[1]
                    newerData = n.split("'")[0]
                    tc = newerData.split("-")[0]
                    tf = newerData.split("-")[1]
                    p = newerData.split("-")[2]
                    pi = newerData.split("-")[3]
                    h = newerData.split("-")[4]
                    dc = newerData.split("-")[5]
                    df = newerData.split("-")[6]
                   # sleep(1)
                    tcFile = open('tc.log','a')
                    tfFile = open('tf.log','a')
                    pFile = open('p.log','a')
                    piFile = open('pi.log','a')
                    hFile = open('h.log','a')
                    dcFile = open('dc.log','a')
                    dfFile = open('df.log','a')
                    print(tc)
                    print(tf)
                    print(p)
                    print(pi)
                    print(h)
                    print(dc)
                    print(df)
                    tcFile.write(tc + '\n')
                    tfFile.write(tf + '\n')
                    pFile.write(p + '\n')
                    piFile.write(pi + '\n')
                    hFile.write(h + '\n')
                    dcFile.write(dc + '\n')
                    dfFile.write(df + '\n')

                    tcFile.close()
                    tfFile.close()
                    pFile.close()
                    piFile.close()
                    hFile.close()
                    dcFile.close()
                    dfFile.close()
                         
                if "USER" in data:
                    print("Connected to user")
                    print("Starting MatLab")
                    eng = matlab.engine.start_matlab()
                    newData = data.strip()
                    n = newData.split(":")[1]
                    newerData = n.split("'")[0]
                    command = newerData.split("-")[0]
                    time = newerData.split("-")[1]

                    if command == "DC":
                        print("Sending DC to user")
                        returned = eng.getOutput(int(time), 'GetData/dc.log')
                        client.send(("The dewpoint will be: " + str(returned)).encode() + "degrees Celsius")
                        print("DC has been sent")
            
                    if command == "DF":
                        print("Sending DF to user")
                        returned = eng.getOutput(int(time), 'GetData/df.log')
                        client.send(("The dewpoint will be: " + str(returned)).encode() + "degrees Farenheit")
                        print("DF has been sent")

                    if command == "TC":
                        print("Sending TC to user")
                        returned = eng.getOutput(int(time), 'GetData/tc.log')
                        client.send(("The temperature will be: " + str(returned)).encode() + "degrees Celsius")
                        print("TC has been sent")

                    if command == "TF":
                        print("Sending TF to user")
                        returned = eng.getOutput(int(time), 'GetData/tf.log')
                        client.send(("The temperature will be: " + str(returned)).encode() + "degrees Farenheit")
                        print("TF has been sent")

                    if command == "Pnches":
                        print("Sending Pnches to user")
                        returned = eng.getOutput(int(time), 'GetData/pi.log')
                        client.send(("The pressure will be: " + str(returned)).encode() + "inches")
                        print("Pnches has been sent")

                    if command == "P":
                        print("Sending P to user")
                        returned = eng.getOutput(int(time), 'GetData/p.log')
                        client.send(("The pressure will be: " + str(returned)).encode())
                        print("P has been sent")

                    if command == "H":
                        print("Sending H to user")
                        returned = eng.getOutput(int(time), 'GetData/h.log')
                        client.send(("The humidity will be: " + str(returned)).encode() + "percent")
                        print("H has been sent")
                 
           
        client.close()
                

if __name__ == "__main__":
    while True:
        try:
            port_num = 12345
            break
        except ValueError:
            pass

    ThreadedServer('',port_num).listen()
