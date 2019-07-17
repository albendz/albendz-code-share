import os

class DataFile:
    def __init__(self, animal_name, filename, path=os.getcwd()):
        """
        The DataFile is initialized with a name of the animal it relates to,
        the file name, and the path where the file is found. The default location
        for the path is the current working directory.
        """
        self.filename = filename
        self.path = path
        self.animal_name = animal_name

    def get_name(self):
        return self.animal_name

    def get_path(self):
        return self.path

    def get_filename(self):
        return self.filename

    def get_full_path(self):
        return os.path.join(self.path, self.filename)

    def get_first_line(self):
        """ Get the metadata line of the file which """
        # If the file is empty, it will return None
        line = None

        try:
            with open(self.get_full_path(), 'r') as f:
                line = f.readline().strip()
            return line
        except FileNotFound:
            # return None if the file is not found
            # Why not check if the file exists before creating this object?
            # It might be useful to refer to files that may not be on disk
            # In that case here we could implement: if file not found, download and read
            return line
