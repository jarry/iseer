from pylibmc.client import Client 
from pylibmc.pools import ClientPool 
#from core.database import BeansDB 
import sys 
import Image
class MC(object): 
    def __init__(self, mc_addrs, pool_size=16, binary=False): 
        mc = Client(mc_addrs, binary=binary) 
        self.mc_pool = ClientPool(mc, pool_size) 
    def get(self, key): 
        with self.mc_pool.reserve() as mc: 
            return mc.get(key) 
    def get_multi(self, keys): 
        with self.mc_pool.reserve() as mc: 
            return mc.get_multi(keys) 
    def set(self, key, data, rev=0): 
        with self.mc_pool.reserve() as mc: 
            return bool(mc.set(key, data, rev)) 
    def delete(self, key): 
        with self.mc_pool.reserve() as mc: 
            return bool(mc.delete(key)) 
class BeansDB(object): 
    @staticmethod 
    def createDB(mc_addrs): 
        if not hasattr(BeansDB, '_mdb'): 
            BeansDB._mdb = MC(mc_addrs) 
    @staticmethod 
    def mdb(): 
        return BeansDB._mdb 

class CacheDB(object): 
    @staticmethod 
    def createDB(mc_addrs): 
        if not hasattr(CacheDB, '_mdb'): 
            CacheDB._mdb = MC(mc_addrs) 
    @staticmethod 
    def mdb(): 
        return CacheDB._mdb 

if __name__ == '__main__': 
    #CacheDB.createDB(['127.0.0.1:11211', '127.0.0.1:11212']) 
    CacheDB.createDB(['127.0.0.1:11211']) 
    #CacheDB.mdb().set('123', 12345)
    print CacheDB.mdb().get('123')
    
