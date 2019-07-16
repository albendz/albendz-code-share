import os

class TrackingFile:
    def __init__(self, subject_name, filename, path=os.getcwd()):
        self.filename = filename
        self.path = path
        self.subject_name = subject_name

    def get_subject(self):
        return self.subject_name

    def get_path(self):
        return self.path

    def get_filename(self):
        return self.filename

    def get_full_path_name(self):
        return os.path.join(self.path, self.filename)

    def get_first_line(self):
        """ Get the metadata line of the file """
        line = None

        with open(self.get_full_path_name(), 'r') as f:
            line = f.readline().strip()

        return line
