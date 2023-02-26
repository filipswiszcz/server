import subprocess
import os
import time

class Worker:

    def __init__(self, user, host):
        self.__user = user
        self.__host = host

    def login(self):
        try:
            command = "ssh {}@{}".format(self.__user, self.__host)
            subprocess.call(command, shell=True)
        except Exception as exception:
            print("Error: {}".format(exception))

    def transfer(self, path, file, destination):
        try:
            command = "scp {}/{} {}@{}:{}".format(self.__get_home_path() + path, file, self.__user, self.__host, destination)
            subprocess.call(command, shell=True)
        except Exception as exception:
            print("Error: {}".format(exception))

    def restart(self):
        try:
            subprocess.Popen(["ssh", "{}@{}".format(self.__user, self.__host), "screen -S server -X stuff '{}\n'".format("stop")])
            self.__wait(5)
            subprocess.Popen(["ssh", "{}@{}".format(self.__user, self.__host), "screen -S server -X stuff '{}\n'".format("./run.sh")])
        except Exception as exception:
            print("Error: {}".format(exception))

    def __get_home_path(self):
        return os.path.expanduser("~")
    
    def __wait(self, seconds):
        time.sleep(seconds)

worker = Worker("filip", "146.59.126.33")
worker.transfer("/Sandbox/server", "server.jar", "server")
worker.restart()