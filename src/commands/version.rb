module Commands
  def self.version
    version_file = File.join(File.dirname(__FILE__), '..', '..', 'VERSION')
    version = File.read(version_file).strip
    puts "Cillers CLI version #{VERSION}"
  end
end
