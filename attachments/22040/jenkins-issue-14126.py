import requests


URLS = (
    'http://ci.petarmaric.com/',
    'http://ci.jenkins-ci.org/',
    'http://ci.djangoproject.com/',
    'https://jenkins.shiningpanda.com/ipython/',
)

LANG_CODES = (
    'en',
    'sr',
    'de',
)


def raises_jelly_tag_exception(url, lang_code):
    r = requests.get(url, headers={'Accept-Language': lang_code})
    return r.status_code == 500 and 'org.apache.commons.jelly.JellyTagException' in r.content

def check(url):
    print "Checking if '%s' raises a JellyTagException:" % url
    for lang_code in LANG_CODES:
        print "\tUsing '%s' language..." % lang_code,
        print 'EXCEPTION!' if raises_jelly_tag_exception(url, lang_code) else 'ok'

def check_all():
    for url in URLS:
        check(url)
        print

if __name__ == '__main__':
    check_all()
