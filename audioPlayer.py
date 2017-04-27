#!/usr/bin/env python3
import sys
import os
import subprocess
import time

songargs = str(sys.argv[1:])
song= ''.join(songargs)
outf, errf = open('out.txt', 'w'), open('err.txt', 'w')
mp = subprocess.Popen('mpsyt .' + song, universal_newlines = True,shell = True, stdin = subprocess.PIPE,stdout = outf,stderr = errf )
mp.communicate(input = '1')

outf.close()
errf.close()
