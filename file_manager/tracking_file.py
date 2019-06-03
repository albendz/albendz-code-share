class TrackingFile:
    def __init__(self, subject_name, filename, path):
        self.filename = filename
        self.path = path
        self.subject_name = subject_name
    
    def get_subject(self):
        return self.subject_name
    
    def get_path(self):
        return self.path
    
    def get_filename(self):
        return self.filename