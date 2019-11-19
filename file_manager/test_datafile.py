import unittest
import os
import tempfile
from datafile import DataFile

class FileTest(unittest.TestCase):

    def test_tracking_file(self):

        test_file = DataFile(self.animal_name, self.filename, self.path)

        self.assertEqual(test_file.get_path(), self.path)
        self.assertEqual(test_file.get_name(), self.animal_name)
        self.assertEqual(test_file.get_filename(), self.filename)

    def test_get_first_line(self):
        test_file = DataFile(self.animal_name, self.filename, self.path)
        first_line = test_file.get_first_line()

        self.assertEqual(first_line, self.animal_name)

    def test_default_path(self):
        test_file = DataFile(self.animal_name, self.filename)
        self.assertEqual(test_file.get_full_path(), os.path.join(os.getcwd(), self.filename))

    def setUp(self):
        self.animal_name = 'TESTANIMALNAME'
        self.filename = 'unittest_file'
        self.path = tempfile.gettempdir()

        with open(os.path.join(self.path, self.filename), 'w') as test_file:
            test_file.write(self.animal_name + '\n')
            test_file.write('Mon Jun  3 07:40:30 PDT 2019, 122.3320708, 7.6062095')

    def tearDown(self):
        os.remove(os.path.join(self.path, self.filename))

if __name__ == '__main__':
    unittest.main()
