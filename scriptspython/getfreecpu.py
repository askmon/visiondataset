import psutil

cpuinfo = psutil.cpu_percent(interval=0.2)
cpuavailable = 100.0 - float(cpuinfo)
print cpuavailable
