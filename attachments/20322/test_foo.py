import unittest

class FooTestCase(unittest.TestCase):
   
    def test_foo(self):
        import time
        time.sleep(1)
        self.assertEqual(1,1)
