import psutil

meminfo = psutil.virtual_memory()
memtotal = float(float(meminfo[0])/1024/1024)
memavailable = float(float(meminfo[1])/1024/1024)
print memavailable
