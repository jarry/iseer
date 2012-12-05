from beanscli import BeansDB, CacheDB
import datetime

BeansDB.createDB(['127.0.0.1:7901'])
CacheDB.createDB(['127.0.0.1:11211'])
con = BeansDB.mdb()
mcon = CacheDB.mdb()

GMT_FORMAT = '%a, %d %b %Y %H:%M:%S GMT'
TEN_YEARS = datetime.timedelta(days=3650)

content_types = {
    'jpg': 'image/jpeg',
    'jpeg': 'image/jpeg',
    'gif': 'image/gif',
    'png': 'image/png'
}


def application(environ, start_response):
    path = environ['PATH_INFO']
    try:
        ext = path[path.rindex('.')+1:].lower()
        ct = content_types[ext] 
        mdata = mcon.get(path)
        data = None 
        if not mdata:
            data = con.get(path)
            mcon.set(path, data, 3600 * 24)
        else:
            data = mdata

        if data:
            now = datetime.datetime.utcnow() 
            start_response('200 OK', [
                ('content-type', ct), 
                ('Last-Modified', now.strftime(GMT_FORMAT)),
                ('Expires', (now+TEN_YEARS).strftime(GMT_FORMAT))
                ])
        else:
            raise ValueError
        return (data, )
    except (ValueError, KeyError):
        start_response('404 NOT FOUND', [('content-type', 'text/plain')])
        return ('Not Found', )
    except:
        start_response('500 INTERNAL SERVER ERROR', 
                        [('content-type', 'text/plain')])
        return ('Internal Server Error', )
