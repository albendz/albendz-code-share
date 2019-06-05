class TrackingFile:
    def __init__(self, subject_name, filename, path):
        self.filename = filename
        self.path = path
        self.subject_name = subject_name

        # Make sure the path ends in a forward slash
        if self.path[-1] != '/':
            self.path = self.path + '/'

    def get_subject(self):
        return self.subject_name

    def get_path(self):
        return self.path

    def get_filename(self):
        return self.filename

    def get_first_line(self):
        """ Get the metadata line of the file """
        line = None

        with open(self.path + self.filename, 'r') as f:
            line = f.readline().strip()

        return line
